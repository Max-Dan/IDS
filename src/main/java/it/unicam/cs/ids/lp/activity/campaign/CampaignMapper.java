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
        campaign.setName(campaignRequest.name());
        campaign.setStartDate(LocalDate.now());
        campaign.setEndDate(campaignRequest.end());
        campaign.setCard(card);
        return campaign;
    }
}
