package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity/{activityId}/campaign/{campaignId}/cashback")
@RequiredArgsConstructor
public class CashbackRuleController {

    private final CashbackRuleService cashbackRuleService;

    @PostMapping("/add")
    public ResponseEntity<CashbackRule> setCashback(@PathVariable long activityId, @PathVariable long campaignId, @RequestBody CashbackRuleRequest request) {
        CashbackRule cashbackRule = cashbackRuleService.setCashbackToCampaign(activityId, campaignId, request);
        return ResponseEntity.ok(cashbackRule);
    }
}
