package com.web.coupon.kafka;

import com.web.coupon.domain.Coupon;
import com.web.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponCreateConsumer {
    private final CouponRepository couponRepository;

    //데이터를 가져올 리스너
    @KafkaListener(topics = "coupon_create",
                   groupId = "group_1")
    public void listener(Long userId) {
        couponRepository.save(new Coupon(userId));
    }
}
