package com.web.order.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.web.item.entity.Item;
import com.web.item.repository.ItemRepository;
import com.web.member.entity.Address;
import com.web.member.entity.Member;
import com.web.member.repository.MemberRepository;
import com.web.order.repository.OrderRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    private ItemRepository itemRepository;

    private Item item;

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderService orderService;


    @BeforeEach
    public void setUp() {
        item = itemRepository.save(
                Item.builder()
                        .name("테스트")
                        .price(1)
                        .stockQuantity(100)
                        .author("저자")
                        .isbn("123456789")
                        .imagePath("aaaaa")
                        .build()
        );

        memberRepository.save(Member.from(
                "user"
                , "1234"
                , "이름"
                , "이메일"
                , new Address("도시", "도로", "집주소")
        ));

    }

    @AfterEach
    public void reset() {
        orderRepository.deleteAll();
        itemRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("싱글 스레드로 제품 테스트 할수 있다.")
    @WithMockUser(username = "user", roles = {"USER"})
    void singleThreadTest() {
        //given
        Long itemId = item.getId();
        int stockQuantity = item.getStockQuantity();
        int cnt = 0;
        int failCnt = 0;
        //when
        for (int i = 0; i < stockQuantity; i++) {
            try {
                orderService.order(itemId, 1);
                cnt++;
            } catch (Exception e) {
                failCnt++;
                log.error("에러 발생 " + failCnt + " : " + e.getMessage());
            }
        }

        Item itemResult = itemRepository.findById(itemId).orElse(null);

        log.info("주문 가능한 제품 물량 : " + stockQuantity);
        log.info("주문 후 성공한 횟수: " + cnt);
        log.info("주문 후 남은 물량 : " + itemResult.getStockQuantity());
        log.info("실패 횟수 : " + failCnt);

        //then
        assertThat(itemResult.getStockQuantity()).isEqualTo(0);
    }

    @Test
    @DisplayName("다중 스레드로 제품 테스트 할수 있다.")
    @WithMockUser(username = "user", roles = {"USER"})
    void multiThreadTest() throws InterruptedException {
        SecurityContext context = SecurityContextHolder.getContext();
        //given
        Long itemId = item.getId();
        int stockQuantity = item.getStockQuantity();

        //100개의 쓰레드
        int numberOfThreads = 100;
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);
        //쓰레드 풀 32개
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        //when

        for (int i = 0; i < stockQuantity; i++) {
            executorService.execute(() -> {
                try {
                    SecurityContextHolder.setContext(context); // 인증 정보 주입
                    orderService.order(itemId, 1);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    log.error("에러 발생: " + e.getMessage());
                    failCount.incrementAndGet();
                } finally {
                    //자바에서 쓰레드 작업이 끝나고 다음작업이 진행되도록 대기 기능 제공
                    //위는 100개의 쓰레드가 작업이 끝났다면 다음 작업을 실행해라
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        boolean terminated = executorService.awaitTermination(30, TimeUnit.SECONDS); // 모든 작업 종료 대기
        if (!terminated) {
            log.warn("스레드 작업이 제한 시간 내에 완료되지 않았습니다.");
        }

        Item itemResult = itemRepository.findById(itemId).orElse(null);

        //then
        log.info("주문 가능한 제품 물량 : " + stockQuantity);
        log.info("주문 후 성공한 횟수: " + successCount.get());
        log.info("주문 후 남은 물량: " + itemResult.getStockQuantity());
        log.info("실패 횟수 : " + failCount.get());

        assertThat(itemResult.getStockQuantity()).isEqualTo(0);
    }
}