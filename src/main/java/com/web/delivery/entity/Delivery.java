package com.web.delivery.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.web.member.entity.Address;
import com.web.order.entity.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

   @OneToOne(fetch = LAZY, mappedBy = "delivery")
    private Order order;

    @Enumerated
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public Delivery(Address address) {
        this.status = DeliveryStatus.READY;
        this.address = address;
    }
}
