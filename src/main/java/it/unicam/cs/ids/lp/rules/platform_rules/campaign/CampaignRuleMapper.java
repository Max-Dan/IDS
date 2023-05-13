package it.unicam.cs.ids.lp.rules.platform_rules.campaign;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.rules.RulesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class CampaignRuleMapper implements BiFunction<Set<RulesEnum>, Campaign, CampaignRule> {

    @Override
    public CampaignRule apply(Set<RulesEnum> rulesEnums, Campaign campaign) {
        CampaignRule campaignRule = new CampaignRule();
        campaignRule.setCampaign(campaign);
        return campaignRule;
    }
}
