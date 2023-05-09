package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class CashbackRuleMapper implements BiFunction<Campaign, CashbackRuleRequest, CashbackRule> {

    @Override
    public CashbackRule apply(Campaign campaign, CashbackRuleRequest cashbackRequest) {
        CashbackRule cashbackRule = new CashbackRule();
        cashbackRule.setCampaign(campaign);
        cashbackRule.setProducts(cashbackRequest.products());
        cashbackRule.setCashbackRate(cashbackRequest.cashbackRate());
        return cashbackRule;
    }
}
