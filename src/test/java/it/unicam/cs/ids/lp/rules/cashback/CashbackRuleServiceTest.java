package it.unicam.cs.ids.lp.rules.cashback;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRepository;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRequest;
import it.unicam.cs.ids.lp.activity.campaign.CampaignService;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.activity.card.CardRequest;
import it.unicam.cs.ids.lp.activity.card.CardService;
import it.unicam.cs.ids.lp.activity.product.ProductRepository;
import it.unicam.cs.ids.lp.activity.product.ProductRequest;
import it.unicam.cs.ids.lp.activity.product.ProductService;
import it.unicam.cs.ids.lp.activity.registration.ActivityRegistrationService;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.CustomerService;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardRequest;
import it.unicam.cs.ids.lp.client.card.CustomerCardService;
import it.unicam.cs.ids.lp.client.coupon.Coupon;
import it.unicam.cs.ids.lp.client.coupon.CouponRepository;
import it.unicam.cs.ids.lp.client.coupon.CouponRequest;
import it.unicam.cs.ids.lp.client.coupon.CouponService;
import it.unicam.cs.ids.lp.client.order.CustomerOrderMapper;
import it.unicam.cs.ids.lp.client.order.CustomerOrderRepository;
import it.unicam.cs.ids.lp.rules.Rule;
import it.unicam.cs.ids.lp.rules.platform_rules.AbstractPlatformRule;
import it.unicam.cs.ids.lp.rules.platform_rules.AbstractPlatformRuleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

@SpringBootTest
class CashbackRuleServiceTest {

    private Activity activity;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ActivityRegistrationService activityRegistrationService;
    @Autowired
    private CardService cardService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CampaignService campaignService;
    @Autowired
    private CashbackRuleService cashbackRuleService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerOrderMapper customerOrderMapper;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private CustomerCardService customerCardService;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CashbackRuleRepository cashbackRuleRepository;
    @Autowired
    private AbstractPlatformRuleRepository abstractPlatformRuleRepository;
    @Autowired
    private CardRepository cardRepository;

    @BeforeEach
    void setUp() {
        activity = activityRegistrationService.register(new Activity()).orElseThrow();
        cardService.createCard(activity.getId(), new CardRequest(""));
        productService.createProduct(activity.getId(), new ProductRequest("", 200));
        productService.createProduct(activity.getId(), new ProductRequest("", 600));
        activity = activityRepository.findById(activity.getId()).orElseThrow();
    }

    @Test
    void setCouponCashback() {
        Customer customer = customerRepository.save(new Customer());
        CustomerCard customerCard = customerCardService.createCustomerCard(
                new CustomerCardRequest(customer.getId(), activity.getCard().getId(), false, null));

        Coupon coupon = couponService.createCoupon(customerCard.getId(), new CouponRequest(null));
        Rule<Integer> rule = cashbackRuleService.setCouponCashback(coupon.getId(), new CashbackRuleRequest(new HashSet<>(productRepository.findByActivities_Id(activity.getId())), 5));

        Assertions.assertTrue(coupon.getCouponRules().stream()
                .map(AbstractPlatformRule::getRule)
                .allMatch(rule1 -> rule1.equals(rule)));

    }

    @Test
    void setCampaignCashback() {
        Campaign campaign = campaignService.createCampaign(activity.getId(), new CampaignRequest("", null));
        Rule<Integer> rule = cashbackRuleService.setCampaignCashback(activity.getId(), campaign.getId(), new CashbackRuleRequest(new HashSet<>(productRepository.findByActivities_Id(activity.getId())), 5));

        //TODO mettere set di regole in campaign
//        Assertions.assertTrue(abstractPlatformRuleRepository.findAll()
//                .stream()
//                        .filter()
//                .map(AbstractPlatformRule::getRule)
//                .allMatch(rule1 -> rule1.equals(rule)));
    }

    @Test
    void setReferralCashback() {
        Rule<Integer> rule = cashbackRuleService.setReferralCashback(activity.getId(), new CashbackReferralRequest(5));

        Card card = cardRepository.findByActivities_Id(activity.getId()).orElseThrow();
        Assertions.assertFalse(card.getReferralRules().isEmpty());
        Assertions.assertTrue(card.getReferralRules().stream().allMatch(referralRule -> referralRule.equals(rule)));
    }

    @Test
    void deleteReferralCashback() {
        Rule<Integer> rule = cashbackRuleService.setReferralCashback(activity.getId(), new CashbackReferralRequest(5));
        cashbackRuleService.deleteReferralCashback(activity.getId(), rule.getId());

        Card card = cardRepository.findByActivities_Id(activity.getId()).orElseThrow();
        Assertions.assertTrue(card.getReferralRules().isEmpty());
    }

    @Test
    void deleteCampaignCashback() {
        Campaign campaign = campaignService.createCampaign(activity.getId(), new CampaignRequest("", null));

        cashbackRuleService.deleteCampaignCashback(activity.getId(), campaign.getId());

        //TODO mettere set di regole in campaign
        //Assertions.assertTrue(campaignRepository.findById(campaign.getId()).orElseThrow().ge);
    }

    @Test
    void deleteCouponCashback() {
        Customer customer = customerRepository.save(new Customer());
        CustomerCard customerCard = customerCardService.createCustomerCard(
                new CustomerCardRequest(customer.getId(), activity.getCard().getId(), false, null));

        Coupon coupon = couponService.createCoupon(customerCard.getId(), new CouponRequest(null));
        cashbackRuleService.setCouponCashback(coupon.getId(), new CashbackRuleRequest(new HashSet<>(productRepository.findByActivities_Id(activity.getId())), 5));

        Assertions.assertFalse(couponRepository.findById(coupon.getId()).orElseThrow().getCouponRules().isEmpty());
        cashbackRuleService.deleteCouponCashback(coupon.getId());
        Assertions.assertTrue(couponRepository.findById(coupon.getId()).orElseThrow().getCouponRules().isEmpty());
    }
}
