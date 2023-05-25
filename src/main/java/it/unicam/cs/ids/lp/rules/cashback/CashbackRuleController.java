package it.unicam.cs.ids.lp.rules.cashback;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity/{activityId}")
@RequiredArgsConstructor
public class CashbackRuleController {

    private final CashbackRuleService cashbackRuleService;

    @PostMapping("/cashback/addCashback")
    public ResponseEntity<CashbackRule> setReferralCashback(@PathVariable long activityId,
                                                            @RequestBody CashbackReferralRequest request) {
        CashbackRule cashbackRule = cashbackRuleService.setReferralCashback(activityId, request);
        return ResponseEntity.ok(cashbackRule);
    }

    @DeleteMapping("/cashback/deleteCashback/{referralId}")
    public ResponseEntity<String> deleteReferralCashback(@PathVariable long activityId,
                                                         @PathVariable long referralId) {
        cashbackRuleService.deleteReferralCashback(activityId, referralId);
        return ResponseEntity.ok("");
    }

    @PostMapping("/campaign/{campaignId}/cashback/add")
    public ResponseEntity<CashbackRule> setCampaignCashback(@PathVariable long activityId, @PathVariable long campaignId, @RequestBody CashbackRuleRequest request) {
        CashbackRule cashbackRule = cashbackRuleService.setCampaignCashback(activityId, campaignId, request);
        return ResponseEntity.ok(cashbackRule);
    }

    @DeleteMapping("/campaign/{campaignId}/cashback/delete")
    public ResponseEntity<CashbackRule> deleteCampaignCashback(@PathVariable long activityId, @PathVariable long campaignId) {
        CashbackRule cashbackRule = cashbackRuleService.deleteCampaignCashback(activityId, campaignId);
        return ResponseEntity.ok(cashbackRule);
    }

    @PostMapping("/coupon/{couponId}/cashback/add")
    public ResponseEntity<CashbackRule> setCouponCashback(@PathVariable long activityId, @PathVariable long couponId,
                                                          @RequestBody CashbackRuleRequest request) {
        CashbackRule cashbackRule = cashbackRuleService.setCouponCashback(couponId, request);
        return ResponseEntity.ok(cashbackRule);
    }

    @DeleteMapping("/coupon/{couponId}/cashback/delete")
    public ResponseEntity<CashbackRule> deleteCouponCashback(@PathVariable long activityId, @PathVariable long couponId) {
        CashbackRule cashbackRule = cashbackRuleService.deleteCouponCashback(couponId);
        return ResponseEntity.ok(cashbackRule);
    }
}
