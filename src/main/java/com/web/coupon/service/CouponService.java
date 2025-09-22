package com.web.coupon.service;

import com.web.coupon.dto.CouponRequest;
import com.web.coupon.dto.IssueCouponRequest;
import com.web.coupon.entity.Coupon;
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
    private final CouponRedisRepository couponRedisRepository;
    private final CouponCreateProducer couponCreateProducer;
    private final CouponRepository couponRepository;

    @Transactional
    public void save(CouponRequest couponRequest) {
        couponRepository.save(couponRequest.toCoupon());
    }

    public void issueCoupon(IssueCouponRequest request) {
        //쿠폰을 처음 발급 받는다면 1을 리턴하고 발급 받은적이 있으면 0을 반환한다
        //유저별로 한개만 쿠폰이 발급된다.
        if (couponRedisRepository.add(request.getUserId()) != 1) {
            return;
        }

        Long couponAmount = couponRedisRepository.increment(request);
        Long couponCount = couponRedisRepository.getOrInitCouponCount(request);

        if(couponAmount > couponCount){
            return;
        }

        couponCreateProducer.crate(request);
    }
}
