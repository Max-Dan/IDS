package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRepository;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity/{activityId}/campaign/{campaignId}/cashback")
public class CashbackRuleController {
    private final CashbackRuleRepository cashbackRuleRepository;
    private final CampaignRepository campaignRepository;
    private final CardRepository cardRepository;

    public CashbackRuleController(CashbackRuleRepository cashbackRuleRepository,
                                  CampaignRepository campaignRepository,
                                  CardRepository cardRepository) {
        this.cashbackRuleRepository = cashbackRuleRepository;
        this.campaignRepository = campaignRepository;
        this.cardRepository = cardRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<CashbackRule> setCashback(@PathVariable long activityId, @PathVariable long campaignId, @RequestBody CashbackRequest request) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        if (!campaign.getCard().equals(cardRepository.findByActivities_Id(activityId).orElseThrow()))
            throw new RuntimeException("Attività non autorizzata a modificare la campagna");
        CashbackRule cashbackRule = getCashbackRule(campaignId, request);
        cashbackRuleRepository.save(cashbackRule);
        return ResponseEntity.ok(cashbackRule);
    }

    private CashbackRule getCashbackRule(long campaignId, CashbackRequest request) {
        CashbackRule cashbackRule = new CashbackRule();
        cashbackRule.setCampaign(campaignRepository.findById(campaignId).orElseThrow());
        cashbackRule.setProducts(request.products());
        cashbackRule.setCashbackRate(request.cashbackRate());
        return cashbackRule;
    }
}
