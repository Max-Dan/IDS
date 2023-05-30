package it.unicam.cs.ids.lp.rules.cashback;

import java.util.Set;

public record CashbackRuleRequest(Set<Long> products,
                                  float cashbackRate) {
}
