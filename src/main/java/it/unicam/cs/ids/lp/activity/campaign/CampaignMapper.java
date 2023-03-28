package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.card.Card;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.BiFunction;

@Service
public class CampaignMapper implements BiFunction<CampaignRequest, Card, Campaign> {

    @Override
    public Campaign apply(CampaignRequest campaignRequest, Card card) {
        Campaign campaign = new Campaign();
        campaign.setName(campaignRequest.name());
        campaign.setStart(LocalDate.now());
        campaign.setEnd(campaignRequest.end());
        campaign.setCard(card);
        Set<Campaign> campaigns = card.getCampaigns();
        campaigns.add(campaign);
        card.setCampaigns(campaigns);
        return campaign;
    }
}
