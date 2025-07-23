package com.web.payment.facade;

import com.web.payment.eventListener.PaymentCancelEvent;
import com.web.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelFacadeEvent {
    private final PaymentService paymentService;
    private final  ApplicationEventPublisher eventPublisher;

    public void cancelEvent(String impUid) {
        //트랜잭션 분리
        paymentService.cancel(impUid);

        //pg사 결제 취소 요청(비동기로 처리)
        eventPublisher.publishEvent(new PaymentCancelEvent(impUid));
    }
}
