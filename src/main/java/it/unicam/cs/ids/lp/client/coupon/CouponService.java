package it.unicam.cs.ids.lp.client.coupon;

import it.unicam.cs.ids.lp.client.Customer;
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

    /**
     * Restituisce il coupon posseduto da un cliente
     *
     * @param customerId id del cliente
     * @param couponId   id del coupon
     * @return il coupon posseduto da un cliente
     */
    public Optional<Coupon> getCoupon(long customerId, long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        return coupon.getCustomer().getId() != customerId ? Optional.empty()
                : Optional.of(coupon);
    }

    /**
     * Restituisce tutti i coupon posseduti da un cliente
     *
     * @param customerId id del cliente
     * @return tutti i coupon posseduti da un cliente
     */
    public Set<Coupon> getCoupons(long customerId) {
        return couponRepository.findByCustomer_Id(customerId);
    }

    /**
     * Verifica se il coupon è valido
     *
     * @param couponId id del coupon
     * @return true se il coupon è valido, false altrimenti
     */
    public boolean isCouponValid(long couponId) {
        return couponRepository.findById(couponId)
                .map(coupon -> coupon.getEnd().isBefore(LocalDate.now()))
                .orElse(false);
    }

    /**
     * Applica le regole dei coupon
     *
     * @param couponsId id dei coupon da applicare
     * @param order     ordine del cliente
     * @return lista delle regole applicate e il loro risultato
     */
    public List<String> applyCoupons(Set<Long> couponsId, CustomerOrder order) {
        List<String> coupons = couponRepository.findAllById(couponsId)
                .stream()
                .filter(coupon -> isCouponValid(coupon.getId()))
                .flatMap(coupon -> coupon.getCouponRules().stream())
                .map(rule -> rule.getRule().getClass().getSimpleName() + "   " + rule.getRule().applyRule(order))
                .toList();
        couponRepository.deleteAllById(couponsId);
        return coupons;
    }

    /**
     * Crea un coupon a un cliente
     *
     * @param customerId    id del cliente
     * @param couponRequest dati del coupon
     * @return il coupon creato
     */
    public Coupon createCoupon(long customerId, CouponRequest couponRequest) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        Coupon coupon = new Coupon();
        coupon.setCustomer(customer);
        coupon.setEnd(couponRequest.end());
        couponRepository.save(coupon);
        customer.getCoupons().add(coupon);
        customerRepository.save(customer);
        return coupon;
    }
}
