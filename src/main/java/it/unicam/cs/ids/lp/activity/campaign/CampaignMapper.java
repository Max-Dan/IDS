package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.card.Card;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.BiFunction;

@Service
public class CampaignMapper implements BiFunction<CampaignRequest, Card, Campaign> {

    @Override
    public Campaign apply(CampaignRequest campaignRequest, Card card) {
        Campaign campaign = new Campaign();
        campaign.setDescription(campaignRequest.description());
        campaign.setCategory(campaignRequest.category());
        campaign.setShopUrl(campaign.getShopUrl());
        campaign.setActivityCard(card);
        campaign.setStart(LocalDate.now());
        campaign.setEnd(campaignRequest.end());
        return campaign;
    }
}
