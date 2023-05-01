package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import it.unicam.cs.ids.lp.rules.RuleRepository;
import it.unicam.cs.ids.lp.rules.platform_rules.campaign.CampaignRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CampaignService {

    private final CardRepository cardRepository;
    private final CampaignMapper campaignMapper;
    private final CampaignRepository campaignRepository;
    private final RuleRepository<?> ruleRepository;


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
        return ruleRepository.findAll()
                .stream()
                .filter(rule -> rule.getPlatformRule() instanceof CampaignRule
                        && ((CampaignRule) rule.getPlatformRule()).getCampaign().equals(campaign))
                .map(rule -> rule.getClass().getSimpleName() + "   " + rule.applyRule(order))
                .toList();
    }

    public List<Campaign> getActiveCampaigns() {
        return campaignRepository.findAll()
                .stream()
                .filter(campaign -> campaign.getStart().isBefore(LocalDate.now())
                        && campaign.getEnd().isAfter(LocalDate.now()))
                .toList();
    }
}
