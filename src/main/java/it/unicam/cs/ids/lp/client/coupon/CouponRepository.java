package it.unicam.cs.ids.lp.client.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository
        extends JpaRepository<Coupon, Long> {
}
