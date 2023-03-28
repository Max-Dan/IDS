package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import it.unicam.cs.ids.lp.activity.campaign.Rule;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/activity/{activityId}/campaign/{campaignId}/cashback")
public class CashbackRuleController {
    private final CardRepository cardRepository;

    public CashbackRuleController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @PostMapping("/setRule")
    public ResponseEntity<Rule<?>> setCashback(@PathVariable long activityId, @PathVariable long campaignId, @RequestBody CashbackRequest request) {
        CashbackRule cashbackRule = new CashbackRule();
        Card card = cardRepository.findByActivities_Id(activityId).orElseThrow();
        cashbackRule.setProducts(request.products());
        cashbackRule.setCashbackRate(request.cashbackRate());
        card = cardRepository.getReferenceById(card.getId());
        card.getCampaigns().stream()
                .filter(campaign -> campaign.getId() == campaignId)
                .forEach(campaign -> campaign.setRules(Set.of(cashbackRule)));
        return ResponseEntity.ok(cashbackRule);
    }
}
