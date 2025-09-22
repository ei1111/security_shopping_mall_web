package com.web.coupon.repository;

import com.web.coupon.entity.FailedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedEventRepository extends JpaRepository<FailedEvent,Long> {

}
