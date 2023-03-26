package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
public class CampaignService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CampaignMapper campaignMapper;
    @Autowired
    private CampaignRepository campaignRepository;

    public Campaign createCampaign(long activityId, CampaignRequest campaignRequest) {
        Objects.requireNonNull(campaignRequest.rules());
        Card card;
        try {
            card = cardRepository.findByActivities_Id(activityId).orElseThrow();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
        Campaign campaign = campaignMapper.apply(campaignRequest, card);
        card.setCampaign(campaign);
        cardRepository.save(card);
        return campaign;
    }

    public Campaign modifyCampaign(long activityId, CampaignRequest campaignRequest) {
        Campaign campaign = campaignRepository.getReferenceById(
                campaignRepository.findByCard_Activities_Id(activityId).getId());
        if (campaignRequest.end() != null
                && campaign.getEnd() != null
                && campaignRequest.end().isAfter(campaign.getEnd()))
            campaign.setEnd(campaignRequest.end());
        return campaign;
    }

    public List<String> applyRules(long activityId, CustomerOrder order) {
        Campaign campaign = campaignRepository.findByCard_Activities_Id(activityId);
        return campaign.getRules().stream()
                .map(rule -> "" + rule.getClass().getName() + "\t" + rule.apply(order))
                .toList();
    }
}
