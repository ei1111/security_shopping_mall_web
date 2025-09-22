package com.web.payment.controller.api;

import com.web.payment.entity.Payment;
import com.web.payment.form.PaymentRequest;
import com.web.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/payments/v1")
@Tag(name = "4. 결제 API")
public class PaymentApiController {

    private final PaymentService paymentService;

    @PostMapping("/save")
    public ResponseEntity<PaymentRequest> save(@RequestBody PaymentRequest request) {
        paymentService.save(request);
        return ResponseEntity.ok(new PaymentRequest());
    }
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Payment>> list() {
        return ResponseEntity.ok(paymentService.findAll());
    }
}
