package com.web.payment.eventListener;

import com.web.payment.util.PaymentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCancelEventListener {

    private final PaymentClient paymentClient;

    @Async
    @EventListener
    public void handlePaymentCancel(PaymentCancelEvent event) {
        // 외부 결제 API 비동기 호출
        paymentClient.cancelPayment(event.getImpUid());
    }
}
