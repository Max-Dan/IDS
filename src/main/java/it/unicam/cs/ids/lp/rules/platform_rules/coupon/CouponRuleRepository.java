package it.unicam.cs.ids.lp.rules.platform_rules.coupon;

import it.unicam.cs.ids.lp.client.coupon.Coupon;
import it.unicam.cs.ids.lp.rules.platform_rules.AbstractPlatformRuleRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRuleRepository extends AbstractPlatformRuleRepository<CouponRule> {
    CouponRule findByCoupon(Coupon coupon);
}
