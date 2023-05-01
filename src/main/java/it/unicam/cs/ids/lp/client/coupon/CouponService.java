package it.unicam.cs.ids.lp.client.coupon;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.rules.RuleRepository;
import it.unicam.cs.ids.lp.rules.platform_rules.RuleMapper;
import it.unicam.cs.ids.lp.rules.platform_rules.coupon.CouponRule;
import it.unicam.cs.ids.lp.rules.platform_rules.coupon.CouponRuleMapper;
import it.unicam.cs.ids.lp.rules.platform_rules.coupon.CouponRuleRepository;
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
    private final CustomerRepository customerRepository;
    private final RuleMapper ruleMapper;
    private final CouponRuleRepository couponRuleRepository;
    private final CouponRuleMapper couponRuleMapper;

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
        List<String> coupons = couponRepository.findAllById(couponsId)
                .stream()
                .flatMap(coupon -> coupon.getCouponRules().stream())
                .map(CouponRule::getRule)
                .map(rule -> rule.getClass().getSimpleName() + "   " + rule.applyRule(order))
                .toList();

//        ruleRepository.findAll()
//                .stream()
//                .filter(rule -> rule.getPlatformRule() instanceof CouponRule
//                        && couponsId.contains(((CouponRule) rule.getPlatformRule()).getCoupon().getId()))
//                .map(rule -> rule.getClass().getSimpleName() + "   " + rule.applyRule(order))
//                .toList();
        couponRepository.deleteAllById(couponsId);
        return coupons;
    }

    public Coupon createCoupon(long customerId, CouponRequest couponRequest) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        Coupon coupon = new Coupon();
        coupon.setCustomer(customer);
        coupon.setEnd(couponRequest.end());
        couponRepository.save(coupon);
        customer.getCoupons().add(coupon);
        customerRepository.save(customer);

        couponRequest.rulesEnums()
                .stream()
                .map(rulesEnum -> couponRuleMapper.apply(rulesEnum, coupon))
                .forEach(couponRule -> {
                    couponRuleRepository.save(couponRule);
                    coupon.getCouponRules().add(couponRule);
                    couponRepository.save(coupon);
                });
        return coupon;
    }
}
