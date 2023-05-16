package it.unicam.cs.ids.lp.client.coupon;

import it.unicam.cs.ids.lp.client.card.CustomerCard;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

    public Coupon mapCoupon(CouponRequest couponRequest, CustomerCard customerCard) {
        Coupon coupon = new Coupon();
        coupon.setCustomerCard(customerCard);
        coupon.setEnd(couponRequest.end());
        return coupon;
    }
}
