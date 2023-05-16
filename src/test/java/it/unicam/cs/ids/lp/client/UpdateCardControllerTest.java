package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.activity.card.CardProgram;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCardUpdateRequest;
import it.unicam.cs.ids.lp.client.card.UpdateCardController;
import it.unicam.cs.ids.lp.client.card.programs.CashbackCard;
import it.unicam.cs.ids.lp.client.card.programs.MembershipCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

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
        CustomerCard customerCard = createTestCashbackCard();

        CustomerCardUpdateRequest request = new CustomerCardUpdateRequest();
        request.setCustomerCardId(customerCard.getId());
        request.setNewProgram(CardProgram.CASHBACK);

        ResponseEntity<?> response = updateCardController.modifyProgram(request);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Optional<CustomerCard> updatedCard = customerCardRepository.findByReferralCode(customerCard.getCustomer().getId()
                + "-" + customerCard.getCard().getId()
                + "-" + CardProgram.CASHBACK);
        Assertions.assertTrue(updatedCard.isPresent());
        Assertions.assertEquals(CardProgram.CASHBACK, updatedCard.get().getProgram());
    }

    @Test
    void modifyAttributes() {
        CashbackCard customerCard = createTestCashbackCard();

        CustomerCardUpdateRequest request = new CustomerCardUpdateRequest();
        request.setCustomerCardId(customerCard.getId());
        request.setFamily(true);
        request.setRemainingCashback(50);

        ResponseEntity<?> response = updateCardController.modifyAttributes(request);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Optional<CustomerCard> updatedCard = customerCardRepository.findById(customerCard.getId());
        Assertions.assertTrue(updatedCard.isPresent());
        Assertions.assertTrue(updatedCard.get().isFamily());
        Assertions.assertEquals(50, ((CashbackCard) updatedCard.get()).getRemainingCashback());

        MembershipCard membershipCard = createTestMembershipCard();
        request.setCustomerCardId(membershipCard.getId());
        request.setMembership(LocalDate.now().plusMonths(1));

        response = updateCardController.modifyAttributes(request);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        updatedCard = customerCardRepository.findById(membershipCard.getId());
        Assertions.assertTrue(updatedCard.isPresent());
        Assertions.assertEquals(LocalDate.now().plusMonths(1), ((MembershipCard) updatedCard.get()).getMembership());
    }

    private CashbackCard createTestCashbackCard() {
        Customer testCustomer = new Customer();
        testCustomer = customerRepository.save(testCustomer);

        Card testCard = new Card();
        testCard.setProgram(CardProgram.CASHBACK);
        testCard = cardRepository.save(testCard);

        CashbackCard testCustomerCard = new CashbackCard();
        testCustomerCard.setCustomer(testCustomer);
        testCustomerCard.setCard(testCard);
        testCustomerCard.setProgram(CardProgram.CASHBACK);
        testCustomerCard.setRemainingCashback(0);

        return customerCardRepository.save(testCustomerCard);
    }

    private MembershipCard createTestMembershipCard() {
        Customer testCustomer = new Customer();
        testCustomer = customerRepository.save(testCustomer);

        Card testCard = new Card();
        testCard.setProgram(CardProgram.MEMBERSHIP);
        testCard = cardRepository.save(testCard);

        MembershipCard testCustomerCard = new MembershipCard();
        testCustomerCard.setCustomer(testCustomer);
        testCustomerCard.setCard(testCard);
        testCustomerCard.setProgram(CardProgram.MEMBERSHIP);
        testCustomerCard.setMembership(LocalDate.now());

        return customerCardRepository.save(testCustomerCard);
    }

}
