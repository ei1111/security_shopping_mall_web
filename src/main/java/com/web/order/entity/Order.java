package com.web.order.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.audit.BaseEntity;
import com.web.delivery.entity.Delivery;
import com.web.delivery.entity.DeliveryStatus;
import com.web.member.entity.Member;
import com.web.orderItem.entity.OrderItem;
import com.web.payment.entity.Payment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @OneToMany(fetch = LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @JsonIgnore
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(fetch = LAZY, mappedBy = "order")
    private Payment payment;

   public void setOrderItems(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order from(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order(member, delivery);

        for (OrderItem orderItem : orderItems) {
            order.setOrderItems(orderItem);
        }

        return order;
    }

    private Order(Member member , Delivery delivery) {
        this.member = member;
        this.delivery = delivery;
        this.status = OrderStatus.ORDER;
        this.orderDate = LocalDate.now();
    }

    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다");
        }

        this.status = OrderStatus.CANCEL;

        for (OrderItem orderItem : orderItems) {
            orderItem.canel();
        }
    }

    public void payment() {
        this.status = OrderStatus.PAYMENT;
    }
}
