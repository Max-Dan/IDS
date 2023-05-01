package it.unicam.cs.ids.lp.rules.cashback;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRepository;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.coupon.Coupon;
import it.unicam.cs.ids.lp.client.coupon.CouponRepository;
import it.unicam.cs.ids.lp.rules.platform_rules.campaign.CampaignRule;
import it.unicam.cs.ids.lp.rules.platform_rules.campaign.CampaignRuleRepository;
import it.unicam.cs.ids.lp.rules.platform_rules.coupon.CouponRule;
import it.unicam.cs.ids.lp.rules.platform_rules.coupon.CouponRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity/{activityId}")
@RequiredArgsConstructor
public class CashbackRuleController {

    private final CashbackRuleRepository cashbackRuleRepository;

    private final CardRepository cardRepository;

    private final CashbackRuleMapper cashbackRuleMapper;

    private final CampaignRepository campaignRepository;
    private final CampaignRuleRepository campaignRuleRepository;
    private final CouponRepository couponRepository;
    private final CouponRuleRepository couponRuleRepository;

    @PostMapping("/campaign/{campaignId}/cashback/add")
    public ResponseEntity<CashbackRule> setCampaignCashback(@PathVariable long activityId, @PathVariable long campaignId, @RequestBody CashbackRequest request) {
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

    @PostMapping("/coupon/{couponId}/cashback/add")
    public ResponseEntity<CashbackRule> setCouponCashback(@PathVariable long activityId, @PathVariable long couponId,
                                                          @RequestBody CashbackRequest request) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        CashbackRule cashbackRule = cashbackRuleMapper.apply(request);
        CouponRule couponRule = new CouponRule();
        couponRule.setCoupon(coupon);
        couponRuleRepository.save(couponRule);
        cashbackRule.setPlatformRule(couponRule);
        cashbackRuleRepository.save(cashbackRule);
        return ResponseEntity.ok(cashbackRule);
    }
}
