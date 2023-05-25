package it.unicam.cs.ids.lp.rules.cashback;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRepository;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.coupon.Coupon;
import it.unicam.cs.ids.lp.client.coupon.CouponRepository;
import it.unicam.cs.ids.lp.rules.Rule;
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
        checkActivityCampaign(activityId, campaign);
        CampaignRule campaignRule = new CampaignRule();
        campaignRule.setCampaign(campaign);
        campaignRuleRepository.save(campaignRule);
        CashbackRule cashbackRule = cashbackRuleMapper.apply(request);
        cashbackRule.setPlatformRule(campaignRule);
        cashbackRuleRepository.save(cashbackRule);
        campaignRule.setRule(cashbackRule);
        campaignRuleRepository.save(campaignRule);
        campaign.getCampaignRules().add(campaignRule);
        campaignRepository.save(campaign);
        return cashbackRule;
    }

    private void checkActivityCampaign(long activityId, Campaign campaign) {
        if (!campaign.getCard().equals(cardRepository.findByActivities_Id(activityId).orElseThrow()))
            throw new RuntimeException("Attività non autorizzata a modificare la campagna");
    }

    public CashbackRule setReferralCashback(long activityId, CashbackReferralRequest request) {
        Card card = cardRepository.findByActivities_Id(activityId)
                .orElseThrow(() -> new RuntimeException("Attività non ha una carta"));
        CashbackRule rule = new CashbackRule();
        rule.setReferralCashback(request.referralCashback());
        cashbackRuleRepository.save(rule);
        card.getReferralRules().add(rule);
        cardRepository.save(card);
        return rule;
    }

    public void deleteReferralCashback(long activityId, long referralId) {
        Card card = cardRepository.findByActivities_Id(activityId)
                .orElseThrow(() -> new RuntimeException("Attività non ha una carta"));
        card.getReferralRules().removeIf(referralRule -> referralRule.getId() == referralId);
        cardRepository.save(card);
        cashbackRuleRepository.deleteById(referralId);
    }

    public Rule<?> deleteCampaignCashback(long activityId, long campaignId, long cashbackId) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        checkActivityCampaign(activityId, campaign);
        CampaignRule campaignRule = campaignRuleRepository.findById(cashbackId).orElseThrow();
        campaign.getCampaignRules().remove(campaignRule);
        campaignRepository.save(campaign);
        campaignRuleRepository.deleteById(cashbackId);
        return campaignRule.getRule();
    }

    public Rule<?> deleteCouponCashback(long couponId, long cashbackId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        Rule<Integer> rule = cashbackRuleRepository.findById(cashbackId).orElseThrow();
        coupon.getCouponRules().removeIf(couponRule -> couponRule.getRule().equals(rule));
        couponRepository.save(coupon);
        couponRuleRepository.deleteById(rule.getId());
        return rule;
    }
}
