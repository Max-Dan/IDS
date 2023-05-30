package it.unicam.cs.ids.lp.rules.cashback;

import it.unicam.cs.ids.lp.activity.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class CashbackRuleMapper implements Function<CashbackRuleRequest, CashbackRule> {

    private final ProductRepository productRepository;

    @Override
    public CashbackRule apply(CashbackRuleRequest cashbackRequest) {
        CashbackRule cashbackRule = new CashbackRule();
        cashbackRule.setProducts(new HashSet<>(productRepository.findAllById(cashbackRequest.products())));
        cashbackRule.setCashbackRate(cashbackRequest.cashbackRate());
        return cashbackRule;
    }
}
