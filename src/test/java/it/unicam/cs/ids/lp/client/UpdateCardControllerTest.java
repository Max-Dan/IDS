/*package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardProgram;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCardUpdateRequest;
import it.unicam.cs.ids.lp.client.card.UpdateCardController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@SpringBootTest
class UpdateCardControllerTest {

    @Autowired
    private UpdateCardController updateCardController;

    @Autowired
    private CustomerCardRepository customerCardRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CardRepository cardRepository;


    @Test
    void modifyProgram() {
        CustomerCard customerCard = createTestCustomerCard();

        CustomerCardUpdateRequest request = new CustomerCardUpdateRequest();
        request.setCustomerCardId(customerCard.getId());
        request.setNewProgram(CardProgram.CASHBACK);

        ResponseEntity<?> response = updateCardController.modifyProgram(request);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        CustomerCard updatedCustomerCard = customerCardRepository.findById(customerCard.getId()).orElse(null);
        Assertions.assertNotNull(updatedCustomerCard);
        Assertions.assertEquals(CardProgram.CASHBACK, updatedCustomerCard.getProgram());
    }

    @Test
    void modifyAttributes() {
        CustomerCard customerCard = createTestCustomerCard();

        CustomerCardUpdateRequest request = new CustomerCardUpdateRequest();
        request.setCustomerCardId(customerCard.getId());
        request.setPoints(100);
        request.setTier(2);
        request.setRemainingCashback(50);
        request.setMembership(LocalDate.now().plusMonths(1));
        request.setFamily(true);

        ResponseEntity<?> response = updateCardController.modifyAttributes(request);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        CustomerCard updatedCustomerCard = customerCardRepository.findById(customerCard.getId()).orElse(null);
        Assertions.assertNotNull(updatedCustomerCard);
        Assertions.assertEquals(100, updatedCustomerCard.getPoints());
        Assertions.assertEquals(2, updatedCustomerCard.getTier());
        Assertions.assertEquals(50, updatedCustomerCard.getRemainingCashback());
        Assertions.assertEquals(LocalDate.now().plusMonths(1), updatedCustomerCard.getMembership());
        Assertions.assertTrue(updatedCustomerCard.isFamily());
    }

    // Helper method to create a test CustomerCard instance
    private CustomerCard createTestCustomerCard() {
        // Create and save a test Customer
        Customer testCustomer = new Customer();
        testCustomer = customerRepository.save(testCustomer);

        // Create and save a test Card with CardProgram.POINTS
        Card testCard = new Card();
        testCard.setProgram(CardProgram.POINTS);
        testCard = cardRepository.save(testCard);

        // Create and save a test CustomerCard
        CustomerCard testCustomerCard = new CustomerCard();
        testCustomerCard.setProgram(CardProgram.POINTS);
        testCustomerCard.setCustomer(testCustomer);
        testCustomerCard.setCard(testCard);
        testCustomerCard.setMembership(LocalDate.now());
        testCustomerCard = customerCardRepository.save(testCustomerCard);

        return testCustomerCard;
    }
}
*/