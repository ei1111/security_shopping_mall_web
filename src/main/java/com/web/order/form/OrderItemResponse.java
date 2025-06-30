package com.web.order.form;

import com.web.orderItem.domain.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "주문 상품 항목")
public class OrderItemResponse {

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemResponse(OrderItem orderItem) {
        this.itemName = orderItem.getItem().getName();  // 연관된 item도 LAZY라 주의
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }

    public static List<OrderItemResponse> from(List<OrderItem> items) {
        return items.stream()
                .map(OrderItemResponse::new)
                .toList();
    }
}
