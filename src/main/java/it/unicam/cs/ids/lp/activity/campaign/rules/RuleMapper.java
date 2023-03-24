package it.unicam.cs.ids.lp.activity.campaign.rules;

import it.unicam.cs.ids.lp.activity.campaign.Rule;
import it.unicam.cs.ids.lp.activity.campaign.RulesEnum;
import it.unicam.cs.ids.lp.activity.campaign.rules.cashback.CashbackRule;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RuleMapper implements Function<RulesEnum, Rule<?>> {

    @Override
    public Rule<?> apply(RulesEnum rulesEnum) {
        return switch (rulesEnum) {
            case CASHBACK -> new CashbackRule();
            default -> throw new RuntimeException();
        };
    }
}
