package com.web.payment.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PaymentViewController {

    @GetMapping("/new")
    public String paymentForm() {
        return "payment/paymentForm";
    }

    @GetMapping
    public String paymentMyPage() {
        return "payment/paymentMyPage";
    }
}
