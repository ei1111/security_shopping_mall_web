package com.web.order.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
    ORDER("주문"),
    CANCEL("취소"),
    PAYMENT("결제완료");

    private final String value;
}
