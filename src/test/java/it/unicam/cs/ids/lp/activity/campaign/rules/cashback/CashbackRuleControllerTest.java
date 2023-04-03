package it.unicam.cs.ids.lp.activity.campaign.rules.cashback;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignMapper;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRepository;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRequest;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.activity.product.Product;
import it.unicam.cs.ids.lp.activity.product.ProductRepository;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
public class CashbackRuleControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CampaignMapper campaignMapper;
    private final String activityName = this.getClass().getName();
    @Autowired
    private CashbackRuleRepository cashbackRuleRepository;
    @Autowired
    private ProductRepository productRepository;
    private Activity activity;
    private Campaign campaign;

    @BeforeAll
    public void setUp() {
        Activity activity1 = activityRepository.findByName(activityName);
        if (activity1 != null) {
            activity = activity1;
            campaign = campaignRepository.findByCard_Activities_Id(activity.getId());
            return;
        }
        activity1 = new Activity();
        activity1.setName(activityName);
        activity = activityRepository.save(activity1);

        Card card = new Card();
        card.setActivities(List.of(activity));
        cardRepository.save(card);

        Campaign campaign = campaignMapper.apply(
                new CampaignRequest("test cash", null), card);
        this.campaign = campaignRepository.save(campaign);

        Product p1 = new Product();
        p1.setPrice(200);
        productRepository.save(p1);
        Product p2 = new Product();
        p2.setPrice(600);
        productRepository.save(p2);
    }

    @Test
    public void setCashback() throws Exception {
        CashbackRequest cashbackRequest = new CashbackRequest(products(), 5);
        String ruleJson = mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId() + "/campaign/" +
                                campaign.getId() + "/cashback/add")
                        .content(objectMapper.writeValueAsString(cashbackRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CashbackRule cashbackRule = objectMapper.readValue(ruleJson, CashbackRule.class);
        Assertions.assertNotNull(cashbackRule);
        Assertions.assertTrue(cashbackRuleRepository.existsById(cashbackRule.getId()));
        System.out.println(cashbackRuleRepository.findById(cashbackRule.getId()).orElseThrow());
    }

    private Set<Product> products() {
        return new HashSet<>(productRepository.findAll());
    }
}