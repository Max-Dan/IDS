package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ContentCategory;
import it.unicam.cs.ids.lp.activity.campaign.rules.cashback.CashbackRuleRequest;
import it.unicam.cs.ids.lp.activity.campaign.rules.cashback.CashbackRuleService;
import it.unicam.cs.ids.lp.activity.card.CardRequest;
import it.unicam.cs.ids.lp.activity.card.CardService;
import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.activity.product.ProductRepository;
import it.unicam.cs.ids.lp.activity.product.ProductRequest;
import it.unicam.cs.ids.lp.activity.product.ProductService;
import it.unicam.cs.ids.lp.activity.registration.ActivityRegistrationService;
import it.unicam.cs.ids.lp.client.order.CustomerOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LoyaltyPlatformApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CampaignServiceTest {

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

    @BeforeAll
    public void setUp() {
        Activity activity1 = new Activity();
        activity1.setCategory(ContentCategory.TECHNOLOGY);
        activity = activityRegistrationService.register(activity1).orElseThrow();
        cardService.createCard(activity.getId(), new CardRequest(""));

        productService.createProduct(activity.getId(), new ProductRequest("", 200));
        productService.createProduct(activity.getId(), new ProductRequest("", 600));
    }

    @Test
    void createCampaign() {
        CampaignRequest campaignRequest = new CampaignRequest("", null);
        campaignService.createCampaign(activity.getId(), campaignRequest);
        Assertions.assertTrue(campaignRepository.existsByCard_Activities_Id(activity.getId()));
    }

    @Test
    void modifyCampaign() {
        CampaignRequest campaignRequest = new CampaignRequest("", null);
        Campaign campaign = campaignService.createCampaign(activity.getId(), campaignRequest);

        campaignService.modifyCampaign(campaign.getId(), activity.getId(), campaignRequest);
        Assertions.assertEquals("", campaignRepository.findById(campaign.getId()).orElseThrow().getName());
    }

    @Test
    public void applyRules() {
        CampaignRequest campaignRequest = new CampaignRequest("", null);
        Campaign campaign = campaignService.createCampaign(activity.getId(), campaignRequest);
        Set<Product> products = new HashSet<>(productRepository.findByActivities_Id(activity.getId()));
        CashbackRuleRequest cashbackRequest = new CashbackRuleRequest(products, 5);
        cashbackRuleService.setCashbackToCampaign(activity.getId(), campaign.getId(), cashbackRequest);
        CustomerOrder order = new CustomerOrder();
        order.setProducts(products);
        List<String> strings = campaignService.applyRules(campaign.getId(), activity.getId(), order);
        Assertions.assertEquals(1, strings.size()); // non deve essere "[]"
    }

    @Test
    public void getAllCampaigns() {
        CampaignRequest campaignRequest = new CampaignRequest("", LocalDate.EPOCH);
        campaignService.createCampaign(activity.getId(), campaignRequest);
        campaignService.createCampaign(activity.getId(), campaignRequest);
        campaignRequest = new CampaignRequest("", null);
        campaignService.createCampaign(activity.getId(), campaignRequest);

        List<Campaign> allCampaigns = campaignService.getAllCampaigns(activity.getId());
        Assertions.assertEquals(3, allCampaigns.size());
    }
}
