package com.web.payment.eventListener;

import com.web.payment.util.PaymentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PaymentCancelEventListener {

    private final PaymentClient paymentClient;

    //우리 서버의 canel 로직 커밋 후 외부 API 취소 요청 발생
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePaymentCancel(PaymentCancelEvent event) {
        // 외부 결제 API 비동기 호출
        paymentClient.cancelPayment(event.getImpUid());
    }
}
