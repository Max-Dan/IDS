package it.unicam.cs.ids.lp.client.coupon;

import it.unicam.cs.ids.lp.rules.RulesEnum;

import java.time.LocalDate;
import java.util.Set;

public record CouponRequest(Set<RulesEnum> rulesEnums, LocalDate end) {
}
