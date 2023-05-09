package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRepository;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashbackRuleService {

    private final CashbackRuleRepository cashbackRuleRepository;

    private final CardRepository cardRepository;

    private final CashbackRuleMapper cashbackRuleMapper;

    private final CampaignRepository campaignRepository;

    public CashbackRule setCashbackToCampaign(long activityId, long campaignId, CashbackRuleRequest request) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        if (!campaign.getCard().equals(cardRepository.findByActivities_Id(activityId).orElseThrow()))
            throw new RuntimeException("Attività non autorizzata a modificare la campagna");
        CashbackRule cashbackRule = cashbackRuleMapper.apply(campaign, request);
        cashbackRuleRepository.save(cashbackRule);
        return cashbackRule;
    }
}
