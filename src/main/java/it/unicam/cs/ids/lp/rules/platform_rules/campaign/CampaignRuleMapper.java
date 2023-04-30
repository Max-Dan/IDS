package it.unicam.cs.ids.lp.rules.platform_rules.campaign;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.rules.Rule;
import it.unicam.cs.ids.lp.rules.RulesEnum;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRule;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class CampaignRuleMapper implements BiFunction<Set<RulesEnum>, Campaign, CampaignRule> {

    private final CashbackRuleRepository cashbackRuleRepository;

    @Override
    public CampaignRule apply(Set<RulesEnum> rulesEnums, Campaign campaign) {
        CampaignRule campaignRule = new CampaignRule();
        campaignRule.setCampaign(campaign);
        return campaignRule;
    }

    private Rule<?> setAndSaveCampaignRule(RulesEnum rulesEnum, CampaignRule campaignRule) {
        return switch (rulesEnum) {
            case CASHBACK -> {
                CashbackRule rule = new CashbackRule();
                rule.setPlatformRule(campaignRule);
                yield cashbackRuleRepository.save(rule);
            }
            default -> throw new RuntimeException();
        };
    }
}
