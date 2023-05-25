package it.unicam.cs.ids.lp.client.coupon;

import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardRepository;
import it.unicam.cs.ids.lp.client.card.programs.ProgramDataMapper;
import it.unicam.cs.ids.lp.client.card.programs.ProgramDataRepository;
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
    private final CustomerCardRepository customerCardRepository;
    private final CouponMapper couponMapper;
    private final ProgramDataRepository programDataRepository;
    private final ProgramDataMapper programDataMapper;

    /**
     * Restituisce il coupon posseduto da un cliente
     *
     * @param customerCardId id della carta del cliente
     * @param couponId       id del coupon
     * @return il coupon posseduto da un cliente
     */
    public Optional<Coupon> getCoupon(long customerCardId, long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        return coupon.getCustomerCard().getId() == customerCardId ? Optional.of(coupon)
                : Optional.empty();
    }

    /**
     * Restituisce i coupon della carta del cliente
     *
     * @param customerCardId id della carta del cliente
     * @return la lista di coupon
     */
    public Set<Coupon> getCardCoupons(long customerCardId) {
        return couponRepository.findByCustomerCard_Id(customerCardId);
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
     */
    public void applyCoupons(Set<Long> couponsId, CustomerOrder order) {
        CustomerCard customerCard = couponRepository.findById(couponsId.stream().toList().get(0)).orElseThrow().getCustomerCard();
        List<Coupon> coupons = couponRepository.findAllById(couponsId);
        coupons.stream()
                .filter(coupon -> isCouponValid(coupon.getId()))
                .flatMap(coupon -> coupon.getCouponRules().stream())
                .map(campaignRule -> programDataMapper.map(campaignRule.getRule(), customerCard))
                .peek(programData -> customerCard.getProgramsData().add(programData))
                .forEach(programDataRepository::save);
        coupons.stream()
                .filter(coupon -> isCouponValid(coupon.getId()))
                .flatMap(coupon -> coupon.getCouponRules().stream())
                .forEach(rule -> rule.getRule().applyRule(order, programDataRepository));
        couponRepository.deleteAllById(couponsId);
    }

    public void seeBonus(Set<Long> couponsId, CustomerOrder order) {
        List<Coupon> coupons = couponRepository.findAllById(couponsId);
        coupons.stream()
                .filter(coupon -> isCouponValid(coupon.getId()))
                .flatMap(coupon -> coupon.getCouponRules().stream())
                .forEach(rule -> rule.getRule().seeBonus(order));
    }

    /**
     * Crea un coupon a un cliente
     *
     * @param customerCardId id della carta del cliente
     * @param couponRequest  dati del coupon
     * @return il coupon creato
     */
    public Coupon createCoupon(long customerCardId, CouponRequest couponRequest) {
        CustomerCard customerCard = customerCardRepository.findById(customerCardId).orElseThrow();
        Coupon coupon = couponMapper.mapCoupon(couponRequest, customerCard);
        couponRepository.save(coupon);
        return coupon;
    }
}
