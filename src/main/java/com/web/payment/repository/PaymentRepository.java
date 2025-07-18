package com.web.payment.repository;

import com.web.order.entity.Order;
import com.web.payment.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByImpUid(String impUid);

    Optional<Payment> findByOrder(Order order);
}
