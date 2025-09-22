package com.web.coupon.kafka;

import com.web.coupon.entity.Coupon;
import com.web.coupon.entity.FailedEvent;
import com.web.coupon.repository.CouponRepository;
import com.web.coupon.repository.FailedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponCreateConsumer {
    private final CouponRepository couponRepository;
    private final FailedEventRepository failedEventRepository;

    //데이터를 가져올 리스너
    @KafkaListener(topics = "coupon_create",
                   groupId = "group_1")
    public void listener(Long userId) {
        try {
            couponRepository.save(new Coupon(userId));
        }catch (Exception e){
            //쿠폰 발급 실패시에 저장
            log.error("failed to create coupon = {}", userId);
            failedEventRepository.save(new FailedEvent(userId));
        }
    }
}
