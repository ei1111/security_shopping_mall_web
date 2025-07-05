package com.web.order.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderViewController {
    @GetMapping("/orderList")
    public String orderList() {
        return "order/orderList";
    }
}
