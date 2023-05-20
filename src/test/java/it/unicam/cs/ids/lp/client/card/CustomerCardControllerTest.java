package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.ActivityRepository;
import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.rules.cashback.CashbackReferralRequest;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerCardControllerTest {

    @Autowired
    private CustomerCardController customerCardController;

    @Autowired
    private CustomerCardMapper customerCardMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CustomerCardRepository customerCardRepository;

    @Autowired
    private CashbackRuleService cashbackRuleService;
    @Autowired
    private ActivityRepository activityRepository;

//    @BeforeEach
//    void cleanup() {
//        customerCardRepository.deleteAll();
//        customerRepository.deleteAll();
//        cardRepository.deleteAll();
//    }

    @Test
    void createCustomerCardNoReferral() {
        assertThrows(NullPointerException.class,
                () -> customerCardMapper.apply(null));

        Customer testCustomer = customerRepository.save(new Customer());

        Card testCard = cardRepository.save(new Card());

        CustomerCardRequest customerCardRequest =
                new CustomerCardRequest(
                        testCustomer.getId(),
                        testCard.getId(),
                        false,
                        null
                );

        ResponseEntity<CustomerCard> response = customerCardController.createCustomerCard(customerCardRequest);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(testCustomer, response.getBody().getCustomer());
        Assertions.assertEquals(testCard, response.getBody().getCard());
        Assertions.assertTrue(response.getBody().getProgramsData().isEmpty());

        //Assertions.assertEquals(referredCustomerCard, response.getBody());
    }

    @Test
    void createCustomerCardWithReferral() {
        Customer testCustomer = customerRepository.save(new Customer());
        Activity activity = activityRepository.save(new Activity());

        Card testCard = new Card();
        testCard.setActivities(List.of(activity));
        CustomerCard referredCustomerCard = new CustomerCard();
        referredCustomerCard.setCustomer(testCustomer);
        referredCustomerCard.setCard(testCard);
        referredCustomerCard.setReferralCode(UUID.randomUUID().toString());
        cardRepository.save(testCard);
        cashbackRuleService.setReferralCashback(activity.getId(), new CashbackReferralRequest(5));
        customerCardRepository.save(referredCustomerCard);

        CustomerCardRequest customerCardRequest =
                new CustomerCardRequest(
                        testCustomer.getId(),
                        testCard.getId(),
                        false,
                        referredCustomerCard.getReferralCode()
                );
        ResponseEntity<CustomerCard> response = customerCardController.createCustomerCard(customerCardRequest);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(testCustomer, response.getBody().getCustomer());
        Assertions.assertEquals(testCard, response.getBody().getCard());
        Assertions.assertFalse(response.getBody().getProgramsData().isEmpty());
    }


    @Test
    void deleteCard() {

        Customer testCustomer = new Customer();
        testCustomer = customerRepository.save(testCustomer);

        Card testCard = new Card();
        testCard = cardRepository.save(testCard);

        CustomerCard testCustomerCard = new CustomerCard();
        testCustomerCard.setCustomer(testCustomer);
        testCustomerCard.setCard(testCard);
        testCustomerCard = customerCardRepository.save(testCustomerCard);

        long id = testCustomerCard.getId();

        ResponseEntity<?> response = customerCardController.deleteCard(id);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertFalse(customerCardRepository.findById(id).isPresent());
    }
}
