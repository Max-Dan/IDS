package it.unicam.cs.ids.lp.rules.platform_rules.coupon;

import it.unicam.cs.ids.lp.client.coupon.Coupon;
import it.unicam.cs.ids.lp.rules.RulesEnum;
import it.unicam.cs.ids.lp.rules.platform_rules.RuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class CouponRuleMapper implements BiFunction<RulesEnum, Coupon, CouponRule> {

    private final RuleMapper ruleMapper;

    @Override
    public CouponRule apply(RulesEnum rulesEnum, Coupon coupon) {
        CouponRule couponRule = new CouponRule();
        couponRule.setCoupon(coupon);
        couponRule.setRule(ruleMapper.apply(rulesEnum, couponRule));
        return couponRule;
    }
}
