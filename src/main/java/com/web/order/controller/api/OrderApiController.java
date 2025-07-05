package com.web.order.controller.api;

import com.web.order.form.OrderRequest;
import com.web.order.form.OrderResponse;
import com.web.order.form.OrderSearchRequest;
import com.web.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/v1")
@Tag(name = "3. 주문 API")
public class OrderApiController {

    private final OrderService orderService;


    @PostMapping("/new")
    @Operation(summary = "주문 정보 등록 API")
    public ResponseEntity<String> order(@Valid @RequestBody OrderRequest request) {
        orderService.order(request.itemId(), request.count());
        return ResponseEntity.ok("order success");
    }

    @GetMapping("/list")
    @Operation(summary = "주문 정보 리스트 API")
    public ResponseEntity<List<OrderResponse>> orderSearch(OrderSearchRequest orderSearch) {
        return  ResponseEntity.ok(orderService.orderSearch(orderSearch));
    }

    @PostMapping("/{orderId}/cancel")
    public void cancel(@PathVariable Long orderId) {
        orderService.cancel(orderId);
    }
}
