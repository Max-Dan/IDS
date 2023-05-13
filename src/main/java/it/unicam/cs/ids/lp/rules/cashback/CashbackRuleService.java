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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashbackRuleService {

    private final CouponRepository couponRepository;
    private final CashbackRuleRepository cashbackRuleRepository;
    private final CardRepository cardRepository;
    private final CashbackRuleMapper cashbackRuleMapper;
    private final CampaignRepository campaignRepository;
    private final CouponRuleRepository couponRuleRepository;
    private final CampaignRuleRepository campaignRuleRepository;

    public CashbackRule setCouponCashback(long couponId, CashbackRuleRequest request) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        CashbackRule cashbackRule = cashbackRuleMapper.apply(request);
        CouponRule couponRule = new CouponRule();
        couponRule.setCoupon(coupon);
        couponRuleRepository.save(couponRule);
        cashbackRule.setPlatformRule(couponRule);
        cashbackRuleRepository.save(cashbackRule);
        couponRule.setRule(cashbackRule);
        couponRuleRepository.save(couponRule);
        coupon.getCouponRules().add(couponRule);
        couponRepository.save(coupon);
        return cashbackRule;
    }

    public CashbackRule setCampaignCashback(long activityId, long campaignId, CashbackRuleRequest request) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        if (!campaign.getCard().equals(cardRepository.findByActivities_Id(activityId).orElseThrow()))
            throw new RuntimeException("Attività non autorizzata a modificare la campagna");
        CampaignRule campaignRule = new CampaignRule();
        campaignRule.setCampaign(campaign);
        campaignRuleRepository.save(campaignRule);
        CashbackRule cashbackRule = cashbackRuleMapper.apply(request);
        cashbackRule.setPlatformRule(campaignRule);
        cashbackRuleRepository.save(cashbackRule);
        campaignRule.setRule(cashbackRule);
        campaignRuleRepository.save(campaignRule);
        return cashbackRule;
    }
}
