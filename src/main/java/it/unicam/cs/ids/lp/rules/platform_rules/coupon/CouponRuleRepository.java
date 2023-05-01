package it.unicam.cs.ids.lp.rules.platform_rules.coupon;

import it.unicam.cs.ids.lp.client.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRuleRepository extends JpaRepository<CouponRule, Long> {
    CouponRule findByCoupon(Coupon coupon);
}
