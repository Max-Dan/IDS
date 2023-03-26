package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import it.unicam.cs.ids.lp.activity.campaign.Rule;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/activity/{activityId}/campaign/cashback")
public class CashbackRuleController {
    private final CardRepository cardRepository;

    public CashbackRuleController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @PostMapping("/setRule")
    public ResponseEntity<Rule<?>> setCashback(@PathVariable long activityId, @RequestBody CashbackRequest request) {
        CashbackRule cashbackRule = new CashbackRule();
        Card card = cardRepository.findByActivities_Id(activityId).orElseThrow();
        cashbackRule.setProducts(request.products());
        cashbackRule.setCashbackRate(request.cashbackRate());
        card = cardRepository.getReferenceById(card.getId());
        card.getCampaign().setRules(
                card.getCampaign().getRules()
                        .stream()
                        .map(rule -> rule instanceof CashbackRule ? cashbackRule : rule)
                        .collect(Collectors.toSet())
        );
        return ResponseEntity.ok(cashbackRule);
    }
}
