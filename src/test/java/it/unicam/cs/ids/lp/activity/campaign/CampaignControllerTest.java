package it.unicam.cs.ids.lp.activity.campaign;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ContentCategory;
import it.unicam.cs.ids.lp.activity.campaign.rules.cashback.CashbackRequest;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
class CampaignControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
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
    void createCampaignSuccess() throws Exception {
        CampaignRequest campaignRequest = new CampaignRequest("", null);
        mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId() + "/campaign/addCampaign")
                .content(objectMapper.writeValueAsString(campaignRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        Assertions.assertTrue(campaignRepository.existsByCard_Activities_Id(activity.getId()));
    }

    @Test
    public void createCampaignFail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/activity/" + Long.MAX_VALUE + "/campaign/addCampaign")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void modifyCampaign() throws Exception {
        CampaignRequest campaignRequest = new CampaignRequest("", null);
        String genCampaign = mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId() + "/campaign/addCampaign")
                .content(objectMapper.writeValueAsString(campaignRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getContentAsString();
        Campaign campaign = objectMapper.readValue(genCampaign, Campaign.class);

        mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId() + "/campaign/" + campaign.getId() + "/modifyData")
                .content(objectMapper.writeValueAsString(campaignRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void applyRules() throws Exception {
        CampaignRequest campaignRequest = new CampaignRequest("", null);
        String campaignJson = mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId() + "/campaign/addCampaign")
                        .content(objectMapper.writeValueAsString(campaignRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Campaign campaign = objectMapper.readValue(campaignJson, Campaign.class);

        Set<Product> products = new HashSet<>(productRepository.findByActivities_Id(activity.getId()));
        CashbackRequest cashbackRequest = new CashbackRequest(products, 5);
        mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId() + "/campaign/" +
                        campaign.getId() + "/cashback/add")
                .content(objectMapper.writeValueAsString(cashbackRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        CustomerOrder order = new CustomerOrder();
        order.setProducts(products);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId()
                                + "/campaign/" + campaign.getId() + "/applyRules")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().length() > 2); // non deve essere "[]"
    }
}
