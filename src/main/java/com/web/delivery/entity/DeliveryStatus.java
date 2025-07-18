package com.web.delivery.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DeliveryStatus {
      READY("배송준비")
    , COMP("배송완료");

    private final String value;
}
