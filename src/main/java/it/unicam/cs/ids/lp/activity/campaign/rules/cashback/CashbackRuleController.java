package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity/{activityId}/campaign/{campaignId}/cashback")
public class CashbackRuleController {
    private final CardRepository cardRepository;
    private final CashbackRuleRepository cashbackRuleRepository;

    public CashbackRuleController(CardRepository cardRepository,
                                  CashbackRuleRepository cashbackRuleRepository) {
        this.cardRepository = cardRepository;
        this.cashbackRuleRepository = cashbackRuleRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<CashbackRule> setCashback(@PathVariable long activityId, @PathVariable long campaignId, @RequestBody CashbackRequest request) {
        Card card = cardRepository.findByActivities_Id(activityId).orElseThrow();
        CashbackRule cashbackRule = getCashbackRule(campaignId, request, card);
        cashbackRuleRepository.save(cashbackRule);
        // cardRepository.save(card);
        return ResponseEntity.ok(cashbackRule);
    }

    private CashbackRule getCashbackRule(long campaignId, CashbackRequest request, Card card) {
        CashbackRule cashbackRule = new CashbackRule();
        cashbackRule.setCampaign(
                card.getCampaigns()
                        .stream()
                        .filter(campaign -> campaign.getId() == campaignId)
                        .findFirst().orElseThrow()
        );
        cashbackRule.setProducts(request.products());
        cashbackRule.setCashbackRate(request.cashbackRate());
        return cashbackRule;
    }
}
