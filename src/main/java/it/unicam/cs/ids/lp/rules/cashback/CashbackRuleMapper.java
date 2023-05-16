package it.unicam.cs.ids.lp.rules.cashback;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CashbackRuleMapper implements Function<CashbackRuleRequest, CashbackRule> {

    @Override
    public CashbackRule apply(CashbackRuleRequest cashbackRequest) {
        CashbackRule cashbackRule = new CashbackRule();
        cashbackRule.setProducts(cashbackRequest.products());
        cashbackRule.setCashbackRate(cashbackRequest.cashbackRate());
        return cashbackRule;
    }
}
