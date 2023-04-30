package it.unicam.cs.ids.lp.rules.platform_rules.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRuleRepository extends JpaRepository<CouponRule, Long> {
    List<CouponRule> findByCoupon_Id(long id);

}
