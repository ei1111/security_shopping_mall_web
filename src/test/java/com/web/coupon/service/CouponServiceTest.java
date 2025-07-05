/*
package com.web.coupon.service;


import static org.assertj.core.api.Assertions.assertThat;

import com.web.coupon.repository.CouponRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("한번만 쿠폰 응모")
    @Test
    void 한개의_쿠폰을_만들수_있다() throws Exception {
        //given
        couponService.issueCoupon(1L);
        //when
        long count = couponRepository.count();
        //then
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("1000개의 쿠폰 응모")
    @Test
    void 천개의_쿠폰을_만들수_있다() throws Exception {
        //given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        //다른 쓰레드에서 수행하는 작업의 요청이 끝날때 까지 기다려주는 클래스
        CountDownLatch latch = new CountDownLatch(threadCount);
        //when
        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                        try {
                            couponService.issueCoupon(userId);
                        } finally {
                            latch.countDown();
                        }
                    }
            );
        }
        latch.await();
        //카프카를 사용하면 API에서 직접 쿠폰을 생성할때보다 처리량을 조절할 수 있다.
        //하지만 쿠폰생성까지 텀이 발생할 수 있다.
        Thread.sleep(10000);
        //then
        long count = couponRepository.count();
        assertThat(count).isEqualTo(100);
    }

    @DisplayName("한명당_한개의_쿠폰을_만들_수_있다")
    @Test
    void 한명당_한개의_쿠폰을_만들_수_있다() throws Exception {
        //given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        //다른 쓰레드에서 수행하는 작업의 요청이 끝날때 까지 기다려주는 클래스
        CountDownLatch latch = new CountDownLatch(threadCount);
        //when
        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                        try {
                            couponService.issueCoupon(1L);
                        } finally {
                            latch.countDown();
                        }
                    }
            );
        }
        latch.await();
        Thread.sleep(10000);
        //then
        long count = couponRepository.count();
        //쿠폰을 한사람당 한개만 발급하도록 제한 하였기에 결과는 1개
        assertThat(count).isEqualTo(1);
    }

}*/
