package it.unicam.cs.ids.lp.client.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CouponRepository
        extends JpaRepository<Coupon, Long> {
    Set<Coupon> findByCustomerCard_Customer_Id(long id);

    Set<Coupon> findByCustomerCard_Id(long id);

}