package com.web.order.repository;

import com.web.order.form.OrderResponse;
import com.web.order.form.OrderSearchRequest;
import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderResponse> orderSearch(OrderSearchRequest request);
}
