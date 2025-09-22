package com.web.coupon.repository;

import com.web.coupon.entity.IssueCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueCouponRepository extends JpaRepository<IssueCoupon, Long> {

}
