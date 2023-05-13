package it.unicam.cs.ids.lp.rules.cashback;

import it.unicam.cs.ids.lp.activity.product.Product;

import java.util.Set;

public record CashbackRuleRequest(Set<Product> products,
                                  float cashbackRate) {
}
