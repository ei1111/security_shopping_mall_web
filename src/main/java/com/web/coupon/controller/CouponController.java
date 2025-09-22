package com.web.coupon.controller;

import com.web.coupon.dto.CouponRequest;
import com.web.coupon.dto.IssueCouponRequest;
import com.web.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "5. 쿠폰 API")
@RequestMapping("/coupon/v1")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupons")
    @Operation(summary = "쿠폰 생성 API")
    public ResponseEntity<Void> save(@RequestBody CouponRequest request) {
        couponService.save(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/coupons/issue")
    @Operation(summary = "쿠폰 발급 API")
    public ResponseEntity<Void> issueCoupon(@RequestBody IssueCouponRequest request) {
        couponService.issueCoupon(request);
        return ResponseEntity.ok().build();
    }
}
