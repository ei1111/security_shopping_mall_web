package com.web.payment.domain;

import com.web.audit.BaseEntity;
import com.web.order.domain.Order;
import com.web.payment.form.PaymentRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Id
    @Comment("결제 번호")
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("회원 아이디")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Comment("주문 번호 아이디")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @Comment("결제가격")
    @Column(name = "payment_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal paymentAmount;

    @Comment("결제 날짜")
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Comment("아이디")
    @Column(name = "imp_uid", length = 50)
    private String impUid;

    @Comment("결제 방식")
    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Comment("결제 고유 번호")
    @Column(name = "merchant_uid", length = 50)
    private String merchantUid;

    @Comment("pg사")
    @Column(name = "pg_provider", length = 20)
    private String pgProvider;

    @Comment("pg 타입")
    @Column(name = "pg_type", length = 20)
    private String pgType;

    @Comment("pg 고유 아이디")
    @Column(name = "pg_tid", length = 50)
    private String pgTid;

    @Comment("결제 상태 값")
    @Column(name = "status", length = 20)
    private String status;

    @Comment("결제 카드")
    @Column(name = "card_name", length = 20)
    private String cardName;

    @Comment("카드 번호")
    @Column(name = "card_number", length = 50)
    private String cardNumber;

    public static Payment of(PaymentRequest request, Order order) {
        return Payment.builder()
                .userId(order.getMember().getId())
                .order(order)
                .impUid(request.getImpUid())
                .paymentDate(LocalDateTime.now())
                .paymentMethod(request.getPayMethod())
                .merchantUid(request.getMerchantUid())
                .paymentAmount(BigDecimal.valueOf(request.getPaidAmount()))
                .pgProvider(request.getPgProvider())
                .pgType(request.getPgType())
                .pgTid(request.getPgTid())
                .status(request.getStatus())
                .cardName(request.getCardName())
                .cardNumber(request.getCardNumber())
                .build();
    }

    public void cancel() {
        this.status = "cancel";
    }
}