package com.web.coupon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueCouponRequest {
    private Long couponId;
    private String userId;
    private String couponName;
    private String couponCode;
}
