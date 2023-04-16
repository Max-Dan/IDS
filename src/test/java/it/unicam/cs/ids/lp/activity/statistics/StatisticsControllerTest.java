package it.unicam.cs.ids.lp.activity.statistics;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.activity.statistics.card.CardStatistic;
import it.unicam.cs.ids.lp.activity.statistics.card.CardStatisticRepository;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LoyaltyPlatformApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class StatisticsControllerTest {

    private final String activityName = this.getClass().getSimpleName();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerCardRepository customerCardRepository;
    @Autowired
    private CardStatisticRepository cardStatisticRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private Activity activity;

    @BeforeAll
    void setUp() {
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

        for (int i = 0; i < 5; i++) {
            Customer customer = new Customer();
            customer.setName("Customer n." + (i + 1));
            customerRepository.save(customer);

            CustomerCard customerCard = new CustomerCard();
            customerCard.setCustomer(customer);
            customerCard.setCard(card);
            customerCardRepository.save(customerCard);

            customer.setCards(Set.of(customerCard));
            customerRepository.save(customer);
        }
    }

    @Test
    void getCardStatistics() throws Exception {
        String string = mvc.perform(get("/activity/" + activity.getId() + "/cardStats"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<CardStatistic> cardStatistics = objectMapper.readValue(string, new TypeReference<>() {
        });

        Assertions.assertTrue(cardStatisticRepository.findAllById(cardStatistics.stream()
                        .map(AbstractStatistic::getId)
                        .toList())
                .stream()
                .allMatch(cardStatistic -> cardStatisticRepository.existsById(cardStatistic.getId())));
    }
}
