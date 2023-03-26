package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.campaign.rules.RuleMapper;
import it.unicam.cs.ids.lp.activity.card.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
public class CampaignMapper implements BiFunction<CampaignRequest, Card, Campaign> {

    @Autowired
    private RuleMapper ruleMapper;

    @Override
    public Campaign apply(CampaignRequest campaignRequest, Card card) {
        Campaign campaign = new Campaign();
        campaign.setName(campaignRequest.name());
        campaign.setStart(LocalDate.now());
        campaign.setEnd(campaignRequest.end());
        campaign.setRules(
                campaignRequest.rules().stream()
                        .map(ruleMapper::apply)
                        .collect(Collectors.toSet())
        );
        campaign.setCard(card);
        return campaign;
    }
}
