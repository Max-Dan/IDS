package it.unicam.cs.ids.lp.client;

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
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardRepository;
import it.unicam.cs.ids.lp.client.registration.CustomerMapper;
import it.unicam.cs.ids.lp.client.registration.CustomerRequest;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LoyaltyPlatformApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CustomerCardRepository customerCardRepository;

    @Autowired
    private CampaignMapper campaignMapper;

    private Customer customer;

    private Campaign campaign;

    private ResultActions mvcPost(CustomerRequest customerRequest) throws Exception {
        return mvc.perform(post("/customer/modifyData/" + customer.getId())
                .content(objectMapper.writeValueAsString(customerRequest))
                .contentType(MediaType.APPLICATION_JSON)
        );
    }

    // con @BeforeEach ogni entità creata viene eliminata automaticamente finito il test, @BeforeAll no
    @BeforeEach
    void setUp() {
        this.customer = customerMapper.apply(new CustomerRequest(
                "gianni",
                "morandi",
                "334-456-2345",
                "gianni.morandi@gmail.com",
                "Prendi il latte"
        ));
        customerRepository.save(customer);

        Activity activity = new Activity();
        activity = activityRepository.save(activity);

        Card card = new Card();
        card.setActivities(List.of(activity));
        cardRepository.save(card);

        campaign = campaignMapper.apply(
                new CampaignRequest("test customer", null), card);
        campaignRepository.save(campaign);

        CustomerCard customerCard = new CustomerCard();
        customerCard.getCampaigns().add(campaign);
        customerCard.setCustomer(customer);
        customerCard.setCard(card);
        customerCardRepository.save(customerCard);

        customer.getCards().add(customerCard);
        customerRepository.save(customer);
    }

    @Test
    void modifyDataName() throws Exception {
        String newName = "Massimo";
        mvcPost(
                new CustomerRequest(
                        newName,
                        null,
                        null,
                        null,
                        null
                )
        ).andExpect(status().isOk());
        Assertions.assertEquals(
                customerRepository.findById(customer.getId()).orElseThrow().getName(),
                newName);
    }

    @Test
    void modifyDataSurname() throws Exception {
        String newSurname = "Ranieri";
        mvcPost(
                new CustomerRequest(
                        null,
                        newSurname,
                        null,
                        null,
                        null
                )
        ).andExpect(status().isOk());
        Assertions.assertEquals(
                customerRepository.findById(customer.getId()).orElseThrow().getSurname(),
                newSurname);
    }

    @Test
    void modifyDataTelephone() throws Exception {
        String newTel = "445-678-6548";
        mvcPost(
                new CustomerRequest(
                        null,
                        null,
                        newTel,
                        null,
                        null
                )
        ).andExpect(status().isOk());
        Assertions.assertEquals(
                customerRepository.findById(customer.getId()).orElseThrow().getTelephoneNumber(),
                newTel);
    }

    @Test
    void modifyDataEmail() throws Exception {
        String newEmail = "massimo.ranieri@gmail.com";
        mvcPost(
                new CustomerRequest(
                        null,
                        null,
                        null,
                        newEmail,
                        null
                )
        ).andExpect(status().isOk());
        Assertions.assertEquals(
                customerRepository.findById(customer.getId()).orElseThrow().getEmail(),
                newEmail);
    }

    @Test
    public void subscribeToCampaignTest() throws Exception {
        mvc.perform(get("/customer/" + customer.getId() + "/subscribeToCampaign/" + campaign.getId()))
                .andExpect(status().isOk());

        Assertions.assertTrue(customerRepository.findById(customer.getId()).orElseThrow()
                .getCurrentlySubscribedCampaigns().contains(campaign));
    }

    @Test
    public void subscribeToCampaignNoCardTest() throws Exception {
        Customer noCardCustomer = new Customer();
        customerRepository.save(noCardCustomer);

        mvc.perform(get("/customer/" + noCardCustomer.getId() + "/subscribeToCampaign/" + campaign.getId()))
                .andExpect(status().isBadRequest());
        Assertions.assertFalse(customerRepository.findById(customer.getId()).orElseThrow()
                .getCurrentlySubscribedCampaigns().contains(campaign));
    }
}
