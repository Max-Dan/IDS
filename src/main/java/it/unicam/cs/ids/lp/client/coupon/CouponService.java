package it.unicam.cs.ids.lp.client.coupon;

import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
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

    private final CustomerRepository customerRepository;

    public Optional<Coupon> getCoupon(long customerId, long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        return coupon.getCustomer().getId() != customerId ? Optional.empty()
                : Optional.of(coupon);
    }

    public Set<Coupon> getCoupons(long customerId) {
        return customerRepository.findById(customerId).orElseThrow().getCoupons();
    }

    public boolean isCouponValid(long couponId) {
        return couponRepository.findById(couponId)
                .map(coupon -> coupon.getEnd().isBefore(LocalDate.now()))
                .orElse(false);
    }

    public List<String> applyCoupons(Set<Long> couponsId, CustomerOrder order) {
        // TODO fare che se un coupon non viene utilizzato non viene eliminato
        List<String> coupons = couponRepository.findAllById(couponsId)
                .stream()
                .map(Coupon::getRules)
                .flatMap(List::stream)
                .map(customerOrderRule -> String.valueOf(customerOrderRule.apply(order)))
                .toList();
        couponRepository.deleteAllById(couponsId);
        return coupons;
    }
}
