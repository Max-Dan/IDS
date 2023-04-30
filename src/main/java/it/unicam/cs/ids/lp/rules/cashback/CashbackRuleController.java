package it.unicam.cs.ids.lp.rules.cashback;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRepository;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.rules.platform_rules.campaign.CampaignRule;
import it.unicam.cs.ids.lp.rules.platform_rules.campaign.CampaignRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity/{activityId}/campaign/{campaignId}/cashback")
@RequiredArgsConstructor
public class CashbackRuleController {

    private final CashbackRuleRepository cashbackRuleRepository;

    private final CardRepository cardRepository;

    private final CashbackRuleMapper cashbackRuleMapper;

    private final CampaignRepository campaignRepository;
    private final CampaignRuleRepository campaignRuleRepository;

    @PostMapping("/add")
    public ResponseEntity<CashbackRule> setCashback(@PathVariable long activityId, @PathVariable long campaignId, @RequestBody CashbackRequest request) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        if (!campaign.getCard().equals(cardRepository.findByActivities_Id(activityId).orElseThrow()))
            throw new RuntimeException("Attività non autorizzata a modificare la campagna");
        CashbackRule cashbackRule = cashbackRuleMapper.apply(request);
        CampaignRule campaignRule = new CampaignRule();
        campaignRule.setCampaign(campaign);
        campaignRuleRepository.save(campaignRule);
        cashbackRule.setPlatformRule(campaignRule);
        cashbackRuleRepository.save(cashbackRule);
        return ResponseEntity.ok(cashbackRule);
    }
}
