package com.web.coupon.service;

import com.web.coupon.domain.Coupon;
import com.web.coupon.kafka.CouponCreateProducer;
import com.web.coupon.repository.CouponRedisRepository;
import com.web.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponRedisRepository couponRedisRepository;
    private final CouponCreateProducer couponCreateProducer;

    public void issueCoupon(Long userId){
        Long couponAmount = couponRedisRepository.increment();

        if(couponAmount > 100){
            return;
        }
        System.out.println("userId = " + userId);
        couponCreateProducer.crate(userId);
    }
}
