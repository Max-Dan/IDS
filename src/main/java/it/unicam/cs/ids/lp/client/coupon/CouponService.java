package it.unicam.cs.ids.lp.client.coupon;

import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.rules.RuleRepository;
import it.unicam.cs.ids.lp.rules.platform_rules.coupon.CouponRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final RuleRepository<?> ruleRepository;

    public Optional<Coupon> getCoupon(long customerId, long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        return coupon.getCustomer().getId() != customerId ? Optional.empty()
                : Optional.of(coupon);
    }

    public Set<Coupon> getCoupons(long customerId) {
        return couponRepository.findByCustomer_Id(customerId);
    }

    public boolean isCouponValid(long couponId) {
        return couponRepository.findById(couponId)
                .map(coupon -> coupon.getEnd().isBefore(LocalDate.now()))
                .orElse(false);
    }

    public List<String> applyCoupons(Set<Long> couponsId, CustomerOrder order) {
        List<String> coupons = ruleRepository.findAll()
                .stream()
                .filter(rule -> rule.getPlatformRule() instanceof CouponRule
                        && couponsId.contains(((CouponRule) rule.getPlatformRule()).getCoupon().getId()))
                .map(rule -> rule.getClass().getSimpleName() + "   " + rule.applyRule(order))
                .toList();
        couponRepository.deleteAllById(couponsId);
        return coupons;
    }
}
