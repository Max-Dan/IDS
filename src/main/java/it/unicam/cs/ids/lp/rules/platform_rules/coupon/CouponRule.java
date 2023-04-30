package it.unicam.cs.ids.lp.rules.platform_rules.coupon;

import it.unicam.cs.ids.lp.client.coupon.Coupon;
import it.unicam.cs.ids.lp.rules.platform_rules.AbstractPlatformRule;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CouponRule extends AbstractPlatformRule {

    @ManyToOne
    @JoinColumn
    private Coupon coupon;
}
