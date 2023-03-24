package it.unicam.cs.ids.lp.activity.campaign;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LoyaltyPlatformApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CampaignControllerTest {


    private final String activityName = "Test campagne";
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
        if (activity1 != null)
            activity = activity1;
        else {
            activity1 = new Activity();
            activity1.setName(activityName);
            Card card = new Card();
            card.setActivities(List.of(activity1));
            cardRepository.save(card);
            activity = activityRepository.save(activity1);
        }
    }

    @Test
    void createCampaignSuccess() throws Exception {
        CampaignRequest campaignRequest = new CampaignRequest(null);
        mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId() + "/campaign/addCampaign")
                .content(objectMapper.writeValueAsString(campaignRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        Assertions.assertTrue(campaignRepository.existsByActivityCard_Activities_Name(activityName));
    }

    @org.junit.Test
    public void createCampaignFail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/activity/" + Long.MAX_VALUE + "/campaign/addCampaign")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void modifyCampaign() throws Exception {
        CampaignRequest campaignRequest = new CampaignRequest(null);
        mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId() + "/campaign/addCampaign")
                .content(objectMapper.writeValueAsString(campaignRequest))
                .contentType(MediaType.APPLICATION_JSON)
        );

        mvc.perform(MockMvcRequestBuilders.post("/activity/" + activity.getId() + "/campaign/modifyData")
                .content(objectMapper.writeValueAsString(campaignRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
