package com.web.payment.portOneUrl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortOneUrl {
    //포트원 토큰 발행
    ACCESS_TOKEN_URL("/users/getToken"),

    //포트원 결제 취소
    CANCEL_PAYMENT_URL("/payments/cancel"),

    //포트원 전체 내역 확인
    CREATE_PAYMENT_URL("/payments/");

    private final String url;
}
