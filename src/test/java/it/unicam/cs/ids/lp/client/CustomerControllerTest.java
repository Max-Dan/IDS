package it.unicam.cs.ids.lp.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRequest;
import it.unicam.cs.ids.lp.activity.campaign.CampaignService;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRequest;
import it.unicam.cs.ids.lp.activity.card.CardService;
import it.unicam.cs.ids.lp.client.card.CustomerCardRequest;
import it.unicam.cs.ids.lp.client.card.CustomerCardService;
import it.unicam.cs.ids.lp.client.registration.CustomerMapper;
import it.unicam.cs.ids.lp.client.registration.CustomerRegistrationService;
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
    private CustomerRegistrationService customerRegistrationService;
    @Autowired
    private CardService cardService;
    @Autowired
    private CampaignService campaignService;
    @Autowired
    private CustomerCardService customerCardService;

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
        this.customer = customerRegistrationService.register(this.customer).orElseThrow();
        Activity activity = new Activity();
        activity = activityRepository.save(activity);
        Card card = cardService.createCard(activity.getId(), new CardRequest(""));
        this.campaign = campaignService.createCampaign(activity.getId(), new CampaignRequest("", null));
        customerCardService.createCustomerCard(new CustomerCardRequest(customer.getId(), card.getId(), false, null));
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
                .getCards()
                .stream()
                .flatMap(card -> card.getCampaigns().stream())
                .anyMatch(campaign1 -> campaign1.equals(campaign)));
    }

    @Test
    public void subscribeToCampaignNoCardTest() throws Exception {
        Customer noCardCustomer = customerMapper.apply(new CustomerRequest(
                "gianni",
                "morandi",
                "334-456-2345",
                "fdasfdsa@dfasdfsd.com",
                "Prendi il latte"
        ));
        noCardCustomer = customerRegistrationService.register(noCardCustomer).orElseThrow();

        mvc.perform(get("/customer/" + noCardCustomer.getId() + "/subscribeToCampaign/" + campaign.getId()))
                .andExpect(status().isBadRequest());
        Assertions.assertFalse(customerRepository.findById(customer.getId()).orElseThrow()
                .getCards()
                .stream()
                .flatMap(card -> card.getCampaigns().stream())
                .anyMatch(campaign1 -> campaign1.equals(campaign)));
    }
}
