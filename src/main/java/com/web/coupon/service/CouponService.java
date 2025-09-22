package com.web.coupon.service;

import com.web.coupon.kafka.CouponCreateProducer;
import com.web.coupon.repository.CouponRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {
    private final CouponRedisRepository couponRedisRepository;
    private final CouponCreateProducer couponCreateProducer;

    public void issueCoupon(Long userId){
        //쿠폰을 처음 발급 받는다면 1을 리턴하고 발급 받은적이 있으면 0을 반환한다
        Long result = couponRedisRepository.add(userId);

        //유저별로 한개만 쿠폰이 발급된다.
        if (result != 1) {
            return;
        }

        Long couponAmount = couponRedisRepository.increment();

        if(couponAmount > 100){
            return;
        }
        couponCreateProducer.crate(userId);
    }
}
