package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.campaign.rules.AbstractRule;
import it.unicam.cs.ids.lp.activity.campaign.rules.AbstractRuleRepository;
import it.unicam.cs.ids.lp.activity.campaign.rules.RuleMapper;
import it.unicam.cs.ids.lp.activity.card.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.BiFunction;

@Service
public class CampaignMapper implements BiFunction<CampaignRequest, Card, Campaign> {

    @Autowired
    private RuleMapper ruleMapper;
    @Autowired
    private AbstractRuleRepository<AbstractRule> abstractRuleRepository;

    @Override
    public Campaign apply(CampaignRequest campaignRequest, Card card) {
        Campaign campaign = new Campaign();
        campaign.setName(campaignRequest.name());
        campaign.setStart(LocalDate.now());
        campaign.setEnd(campaignRequest.end());
//        campaign.setRules(
//                campaignRequest.rules().stream()
//                        .map(ruleMapper::apply)
//                        .collect(Collectors.toSet())
//        );
        campaign.setCard(card);
        Set<Campaign> campaigns = card.getCampaigns();
        campaigns.add(campaign);
        card.setCampaigns(campaigns);
        //abstractRuleRepository.saveAll(campaignRequest.rules());
        return campaign;
    }
}
