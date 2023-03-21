package it.unicam.cs.ids.lp.activity.campaign;

import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class CampaignMapper implements BiFunction<CampaignRequest, Long, Campaign> {

    // TODO metterci la carta
//    @Autowired
//    private CardRepository cardRepository;

    @Override
    public Campaign apply(CampaignRequest campaignRequest, Long activityId) {
        Campaign campaign = new Campaign();
        campaign.setDescription(campaignRequest.description());
        campaign.setCategory(campaignRequest.category());
        campaign.setShopUrl(campaign.getShopUrl());
        //campaign.setCard(cardRepository.findByActivityName(activityId).orElseThrow())
        return campaign;
    }
}
