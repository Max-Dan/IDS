package it.unicam.cs.ids.lp.rules.cashback;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity/{activityId}")
@RequiredArgsConstructor
public class CashbackRuleController {

    private final CashbackRuleService cashbackRuleService;

    @PostMapping("/campaign/{campaignId}/cashback/add")
    public ResponseEntity<CashbackRule> setCampaignCashback(@PathVariable long activityId, @PathVariable long campaignId, @RequestBody CashbackRuleRequest request) {
        CashbackRule cashbackRule = cashbackRuleService.setCampaignCashback(activityId, campaignId, request);
        return ResponseEntity.ok(cashbackRule);
    }

    @PostMapping("/coupon/{couponId}/cashback/add")
    public ResponseEntity<CashbackRule> setCouponCashback(@PathVariable long activityId, @PathVariable long couponId,
                                                          @RequestBody CashbackRuleRequest request) {
        CashbackRule cashbackRule = cashbackRuleService.setCouponCashback(couponId, request);
        return ResponseEntity.ok(cashbackRule);
    }
}
