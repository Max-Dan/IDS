package it.unicam.cs.ids.lp.activity.campaign.rules;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.RulesEnum;
import it.unicam.cs.ids.lp.activity.campaign.rules.cashback.CashbackRule;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class RuleMapper implements BiFunction<RulesEnum, Campaign, AbstractRule<?>> {

    @Override
    public AbstractRule<?> apply(RulesEnum rulesEnum, Campaign campaign) {
        return switch (rulesEnum) {
            case CASHBACK -> setCard(new CashbackRule(), campaign);
            default -> throw new RuntimeException();
        };
    }

    private AbstractRule<?> setCard(AbstractRule<?> abstractRule, Campaign campaign) {
        abstractRule.setCampaign(campaign);
        return abstractRule;
    }
}
