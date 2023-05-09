package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ContentCategory;
import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRequest;
import it.unicam.cs.ids.lp.activity.campaign.CampaignService;
import it.unicam.cs.ids.lp.activity.card.CardRequest;
import it.unicam.cs.ids.lp.activity.card.CardService;
import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.activity.product.ProductRepository;
import it.unicam.cs.ids.lp.activity.product.ProductRequest;
import it.unicam.cs.ids.lp.activity.product.ProductService;
import it.unicam.cs.ids.lp.activity.registration.ActivityRegistrationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LoyaltyPlatformApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class CashbackRuleControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CashbackRuleRepository cashbackRuleRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ActivityRegistrationService activityRegistrationService;
    @Autowired
    private CardService cardService;
    @Autowired
    private CampaignService campaignService;
    @Autowired
    private ProductService productService;
    private Activity activity;
    private Campaign campaign;

    @BeforeEach
    public void setUp() {
        Activity activity1 = new Activity();
        activity1.setCategory(ContentCategory.TECHNOLOGY);
        activity = activityRegistrationService.register(activity1).orElseThrow();
        cardService.createCard(activity.getId(), new CardRequest(""));
        campaign = campaignService.createCampaign(activity.getId(), new CampaignRequest("", null));

        productService.createProduct(activity.getId(), new ProductRequest("", 200));
        productService.createProduct(activity.getId(), new ProductRequest("", 600));
    }

    @Test
    public void setCashback() throws Exception {
        Set<Product> products = new HashSet<>(productRepository.findByActivities_Id(activity.getId()));
        CashbackRuleRequest cashbackRequest = new CashbackRuleRequest(products, 5);
        String ruleJson = mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId()
                                + "/campaign/" + campaign.getId() + "/cashback/add")
                        .content(objectMapper.writeValueAsString(cashbackRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CashbackRule cashbackRule = objectMapper.readValue(ruleJson, CashbackRule.class);
        Assertions.assertNotNull(cashbackRule);
        Assertions.assertTrue(cashbackRuleRepository.existsById(cashbackRule.getId()));
    }
}
