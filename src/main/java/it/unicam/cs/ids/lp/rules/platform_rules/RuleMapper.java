package it.unicam.cs.ids.lp.rules.platform_rules;

import it.unicam.cs.ids.lp.rules.Rule;
import it.unicam.cs.ids.lp.rules.RulesEnum;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRule;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class RuleMapper implements BiFunction<RulesEnum, AbstractPlatformRule, Rule<?>> {

    private final CashbackRuleRepository cashbackRuleRepository;

    @Override
    public Rule<?> apply(RulesEnum rulesEnum, AbstractPlatformRule platformRule) {
        return switch (rulesEnum) {
            case CASHBACK -> {
                CashbackRule rule = new CashbackRule();
                rule.setPlatformRule(platformRule);
                yield cashbackRuleRepository.save(rule);
            }
            default -> throw new RuntimeException();
        };
    }
}
