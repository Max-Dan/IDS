package it.unicam.cs.ids.lp.rules.platform_rules.campaign;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.rules.RulesEnum;
import it.unicam.cs.ids.lp.rules.platform_rules.RuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class CampaignRuleMapper implements BiFunction<RulesEnum, Campaign, CampaignRule> {

    private final RuleMapper ruleMapper;

    @Override
    public CampaignRule apply(RulesEnum rulesEnum, Campaign campaign) {
        CampaignRule campaignRule = new CampaignRule();
        campaignRule.setCampaign(campaign);
        campaignRule.setRule(ruleMapper.apply(rulesEnum, campaignRule));
        return campaignRule;
    }
}
