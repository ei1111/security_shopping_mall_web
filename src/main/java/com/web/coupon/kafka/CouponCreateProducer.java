package com.web.coupon.kafka;

import com.web.coupon.dto.IssueCouponRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponCreateProducer {
    private final KafkaTemplate<String, IssueCouponRequest> kafkaTemplate;

    //토픽에 userId를 전달하기 때문에 userId를 매개변수로 가지는 매소드 생성
    public void crate(IssueCouponRequest issueCouponRequest) {
        kafkaTemplate.send("coupon_create", issueCouponRequest);
    }
}
