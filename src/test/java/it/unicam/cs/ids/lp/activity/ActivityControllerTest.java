package it.unicam.cs.ids.lp.activity;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.registration.ActivityRequest;
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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LoyaltyPlatformApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class ActivityControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityRepository activityRepository;

    private Activity activity;

    @BeforeEach
    void setUp() throws Exception {
        ActivityRequest activityRequest = new ActivityRequest(
                "Amazon",
                "via amazon 3",
                "344-673-9854",
                "amazon@gmail.com",
                ContentCategory.TECHNOLOGY,
                "passwordDiAmazon124"
        );
        mvc.perform(put("/activity/register")
                .content(objectMapper.writeValueAsString(activityRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
        this.activity = activityRepository.findByName("Amazon");
    }

    @Test
    void modifyActivityName() throws Exception {
        ActivityRequest activityRequest = new ActivityRequest(
                "Apple", null, null, null, null, null);
        mvc.perform(post("/activity/" + activity.getId() + "/modifyData")
                .content(objectMapper.writeValueAsString(activityRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertEquals(activityRepository.findById(activity.getId()).orElseThrow().getName()
                , activity.getName());
    }

    @Test
    void modifyActivityAddress() throws Exception {
        ActivityRequest activityRequest = new ActivityRequest(
                null, "via apple 3", null, null, null, null);
        mvc.perform(post("/activity/" + activity.getId() + "/modifyData")
                .content(objectMapper.writeValueAsString(activityRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertEquals(activityRepository.findById(activity.getId()).orElseThrow().getAddress()
                , activity.getAddress());
    }

    @Test
    void modifyActivityTelephoneNumber() throws Exception {
        ActivityRequest activityRequest = new ActivityRequest(
                null, null, "445-678-6548", null, null, null);
        mvc.perform(post("/activity/" + activity.getId() + "/modifyData")
                .content(objectMapper.writeValueAsString(activityRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertEquals(activityRepository.findById(activity.getId()).orElseThrow().getTelephoneNumber()
                , activity.getTelephoneNumber());
    }

    @Test
    void modifyActivityEmail() throws Exception {
        ActivityRequest activityRequest = new ActivityRequest(
                null, null, null, "apple@gmail.com", null, null);
        mvc.perform(post("/activity/" + activity.getId() + "/modifyData")
                .content(objectMapper.writeValueAsString(activityRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertEquals(activityRepository.findById(activity.getId()).orElseThrow().getEmail()
                , activity.getEmail());
    }

    @Test
    void modifyActivityCategory() throws Exception {
        ActivityRequest activityRequest = new ActivityRequest(
                null, null, null, null, ContentCategory.LIFESTYLE, null);
        mvc.perform(post("/activity/" + activity.getId() + "/modifyData")
                .content(objectMapper.writeValueAsString(activityRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertEquals(activityRepository.findById(activity.getId()).orElseThrow().getCategory()
                , activity.getCategory());
    }
}
