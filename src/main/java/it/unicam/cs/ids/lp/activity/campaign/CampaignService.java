package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.campaign.rules.AbstractRuleRepository;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CampaignService {

    private final CardRepository cardRepository;
    private final CampaignMapper campaignMapper;
    private final CampaignRepository campaignRepository;
    private final AbstractRuleRepository<?> abstractRuleRepository;

    public CampaignService(CardRepository cardRepository,
                           CampaignMapper campaignMapper,
                           CampaignRepository campaignRepository,
                           AbstractRuleRepository<?> abstractRuleRepository) {
        this.cardRepository = cardRepository;
        this.campaignMapper = campaignMapper;
        this.campaignRepository = campaignRepository;
        this.abstractRuleRepository = abstractRuleRepository;
    }

    public Campaign createCampaign(long activityId, CampaignRequest campaignRequest) {
        Card card = cardRepository.findByActivities_Id(activityId).orElseThrow();
        Campaign campaign = campaignMapper.apply(campaignRequest, card);
        campaignRepository.save(campaign);
        return campaign;
    }

    public Campaign modifyCampaign(long campaignId, CampaignRequest campaignRequest) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        if (campaignRequest.end() != null
                && campaign.getEnd() != null
                && campaignRequest.end().isAfter(campaign.getEnd()))
            campaign.setEnd(campaignRequest.end());
        return campaign;
    }

    public List<String> applyRules(long campaignId, CustomerOrder order) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        return abstractRuleRepository.findAll()
                .stream()
                .filter(abstractRule -> abstractRule.getCampaign()
                        .equals(campaign))
                .map(rule -> rule.getClass().getSimpleName() + "   " + rule.apply(order))
                .toList();
    }

    public List<Campaign> getActiveCampaigns() {
        return campaignRepository.findAll()
                .stream()
                .filter(campaign -> !campaign.isCurrentlyActive())
                .toList();
    }

    /**
     * Mostra i vantaggi che può portare un ordine senza applicarli
     *
     * @return la lista di vantaggi che può portare
     */
    public List<String> seeBonuses(long campaignId, CustomerOrder order) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        return abstractRuleRepository.findAll()
                .stream()
                .filter(abstractRule -> abstractRule.getCampaign()
                        .equals(campaign))
                .map(rule -> rule.getClass().getSimpleName() + "   " + rule.seeBonus(order))
                .toList();
    }
}
