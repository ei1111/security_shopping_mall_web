package com.web.payment.service;

import com.web.order.entity.Order;
import com.web.order.repository.OrderRepository;
import com.web.payment.entity.Payment;
import com.web.payment.eventListener.PaymentCancelEvent;
import com.web.payment.form.PaymentRequest;
import com.web.payment.repository.PaymentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void save(PaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(IllegalArgumentException::new);
        order.payment();
        paymentRepository.save(Payment.of(request, order));
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Transactional
    public void cancel(String impUid) {
        Payment payment = paymentRepository.findByImpUid(impUid).orElseThrow(
                () -> new IllegalArgumentException("payment not found impUid : " + impUid)
        );

        //status 필드 cancel으로 변경
        payment.cancel();
    }
}
