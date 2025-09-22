package com.web.coupon.dto;

import com.web.coupon.entity.Coupon;
import lombok.Getter;

@Getter
public class CouponRequest {

    private String couponName;
    private String couponCode;
    private Long count;

    public Coupon toCoupon() {
        return Coupon.builder()
                .couponName(couponName)
                .couponCode(couponCode)
                .count(count)
                .build();
    }
}
