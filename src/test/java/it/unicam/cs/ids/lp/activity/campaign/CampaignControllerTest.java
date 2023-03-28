package it.unicam.cs.ids.lp.activity.campaign;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.activity.campaign.rules.cashback.CashbackRequest;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.activity.product.Product;
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

import java.util.List;
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

    private final String activityName = this.getClass().getName();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Activity activity;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private CardRepository cardRepository;

    @BeforeAll
    public void setUp() {
        Activity activity1 = activityRepository.findByName(activityName);
        if (activity1 != null) {
            activity = activity1;
            return;
        }
        activity1 = new Activity();
        activity1.setName(activityName);
        activity = activityRepository.save(activity1);

        activity1 = activityRepository.findById(activity.getId()).orElseThrow();
        Card card = new Card();
        card.setActivities(List.of(activity1));
        cardRepository.save(card);
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

    private Set<Product> products() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setPrice(200);
        Product p2 = new Product();
        p2.setId(2L);
        p2.setPrice(600);
        return Set.of(p1, p2);
    }

    @Test
    public void applyRules() throws Exception {
        CampaignRequest campaignRequest = new CampaignRequest("", null);
        String campaignJson = mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId() + "/campaign/addCampaign")
                .content(objectMapper.writeValueAsString(campaignRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getContentAsString();
        Campaign campaign = objectMapper.readValue(campaignJson, Campaign.class);

        CashbackRequest cashbackRequest = new CashbackRequest(products(), 5);
        mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId() + "/campaign/cashback/setRule")
                .content(objectMapper.writeValueAsString(cashbackRequest))
                .contentType(MediaType.APPLICATION_JSON)
        );

        CustomerOrder order = new CustomerOrder();
        order.setProducts(products());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId()
                                + "/campaign/" + campaign.getId() + "/applyRules")
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().length() > 2); // non deve essere "[]"
    }
}
