package it.unicam.cs.ids.lp.rules.cashback;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class CashbackRuleMapper implements BiFunction<Campaign, CashbackRequest, CashbackRule> {

    @Override
    public CashbackRule apply(Campaign campaign, CashbackRequest cashbackRequest) {
        CashbackRule cashbackRule = new CashbackRule();
        cashbackRule.setProducts(cashbackRequest.products());
        cashbackRule.setCashbackRate(cashbackRequest.cashbackRate());
        return cashbackRule;
    }
}
