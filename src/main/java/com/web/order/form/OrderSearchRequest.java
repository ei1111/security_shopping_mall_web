package com.web.order.form;


import com.web.order.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchRequest {
    private String memberName;
    private OrderStatus orderStatus;
}
