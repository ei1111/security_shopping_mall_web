package com.web.coupon.repository;


import com.web.coupon.dto.IssueCouponRequest;
import com.web.coupon.entity.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRedisRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, Long> redisCountTemplate;
    private final CouponRepository couponRepository;

    public Long increment(IssueCouponRequest request) {
        return redisTemplate
                .opsForValue()
                .increment(getCouponKey(request));
    }

    public Long add(String userId) {
        return redisTemplate
                .opsForSet()
                .add("applied_user", userId);

    }

    public Long getOrInitCouponCount(IssueCouponRequest request) {
        String couponCode = request.getCouponCode();
        Long couponCount = redisCountTemplate.opsForValue().get(couponCode);

        if (couponCount == null) {
            Coupon coupon = couponRepository.findById(request.getCouponId())
                    .orElseThrow(() -> new IllegalArgumentException("쿠폰 없음"));

            redisCountTemplate.opsForValue().set(couponCode, coupon.getCount());
            couponCount = coupon.getCount();
        }

        return couponCount;
    }


    private String getCouponKey(IssueCouponRequest issueCouponRequest) {
        return issueCouponRequest.getCouponName() + ":" + issueCouponRequest.getCouponId();
    }
}
