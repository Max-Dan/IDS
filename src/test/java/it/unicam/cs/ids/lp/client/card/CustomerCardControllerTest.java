package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardProgram;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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


    @Test
    void createCustomerCard() {
        assertThrows(NullPointerException.class,
                () -> customerCardController.createCustomerCard(null));

        Customer testCustomer = new Customer();

        testCustomer = customerRepository.save(testCustomer);

        Card testCard = new Card();

        testCard.setProgram(CardProgram.POINTS);
        testCard = cardRepository.save(testCard);

        CustomerCardRequest customerCardRequest =
                new CustomerCardRequest(
                        testCustomer.getId(),
                        testCard.getId(),
                        CardProgram.POINTS,
                        false,
                        "sampleReferralCode"
                );
        CustomerCard customerCard = customerCardMapper.apply(customerCardRequest);
        ResponseEntity<CustomerCard> response = customerCardController.createCustomerCard(customerCardRequest);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(customerCard.getProgram(), response.getBody().getProgram());
    }

    //@AfterEach
    //void tearDown() {customerCardRepository.deleteAll();
    //}
    @Test
    void deleteCard() {

        Customer testCustomer = new Customer();

        testCustomer = customerRepository.save(testCustomer);

        Card testCard = new Card();

        testCard.setProgram(CardProgram.POINTS);
        testCard = cardRepository.save(testCard);

        CustomerCard testCustomerCard = new CustomerCard();

        testCustomerCard.setProgram(CardProgram.POINTS);
        testCustomerCard.setCustomer(testCustomer);
        testCustomerCard.setCard(testCard);
        testCustomerCard = customerCardRepository.save(testCustomerCard);

        long id = testCustomerCard.getId();

        ResponseEntity<?> response = customerCardController.deleteCard(id);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
