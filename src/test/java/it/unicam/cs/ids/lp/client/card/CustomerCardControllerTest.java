package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void cleanup() {
        customerCardRepository.deleteAll();
        customerRepository.deleteAll();
        cardRepository.deleteAll();
    }

    @Test
    void createCustomerCard() {
        assertThrows(NullPointerException.class,
                () -> customerCardMapper.apply(null));

        Customer testCustomer = new Customer();
        testCustomer = customerRepository.save(testCustomer);

        Card testCard = new Card();
        testCard.setProgram(CardProgram.CASHBACK);
        testCard = cardRepository.save(testCard);

        CustomerCard referredCustomerCard = new CustomerCard();
        referredCustomerCard.setCustomer(testCustomer);
        referredCustomerCard.setCard(testCard);
        referredCustomerCard.setReferralCode("sampleReferralCode");
        referredCustomerCard = customerCardRepository.save(referredCustomerCard);

        CustomerCardRequest customerCardRequest =
                new CustomerCardRequest(
                        testCustomer.getId(),
                        testCard.getId(),
                        CardProgram.CASHBACK,
                        false,
                        referredCustomerCard.getReferralCode()
                );

        ResponseEntity<CustomerCard> response = customerCardController.createCustomerCard(customerCardRequest);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(testCustomer, response.getBody().getCustomer());
        Assertions.assertEquals(testCard, response.getBody().getCard());

        Assertions.assertEquals(referredCustomerCard, response.getBody().getReferredBy());
    }

    @Test
    void deleteCard() {

        Customer testCustomer = new Customer();
        testCustomer = customerRepository.save(testCustomer);

        Card testCard = new Card();
        testCard.setProgram(CardProgram.CASHBACK);
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
