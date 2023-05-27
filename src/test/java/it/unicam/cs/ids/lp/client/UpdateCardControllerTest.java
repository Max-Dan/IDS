package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import it.unicam.cs.ids.lp.client.card.CustomerCardRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCardUpdateRequest;
import it.unicam.cs.ids.lp.client.card.UpdateCardController;
import it.unicam.cs.ids.lp.client.card.programs.CashbackData;
import it.unicam.cs.ids.lp.client.card.programs.ProgramData;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRule;
import it.unicam.cs.ids.lp.rules.cashback.CashbackRuleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.Optional;

@SpringBootTest
class UpdateCardControllerTest {

    @Autowired
    private UpdateCardController updateCardController;

    @Autowired
    private CustomerCardRepository customerCardRepository;

    @Autowired
    private CashbackRuleRepository cashbackRuleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CardRepository cardRepository;

    @Test
    void modifyProgram() {
        CustomerCard customerCard = createCustomerCard();
        CustomerCardUpdateRequest request = new CustomerCardUpdateRequest();
        request.setCustomerCardId(customerCard.getId());
        Assertions.assertTrue(customerCard.getProgramsData().isEmpty());

        CashbackRule newRule = new CashbackRule();
        cashbackRuleRepository.save(newRule);
        request.setProgramRule(newRule);

        ResponseEntity<?> response = updateCardController.modifyProgram(request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        customerCard = customerCardRepository.findById(customerCard.getId()).orElseThrow();
        Assertions.assertTrue(customerCard.getProgramsData().stream()
                .anyMatch(program -> program.getRule().equals(newRule)));

        response = updateCardController.modifyProgram(request);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        customerCard = customerCardRepository.findById(customerCard.getId()).orElseThrow();
        Assertions.assertFalse(customerCard.getProgramsData().stream()
                .anyMatch(program -> program.getRule().getId() == newRule.getId()));
    }


    @Test
    void modifyAttributes() {
        CustomerCard customerCard = createCustomerCard();

        CustomerCardUpdateRequest request = new CustomerCardUpdateRequest();
        request.setCustomerCardId(customerCard.getId());
        request.setFamily(true);
        request.setRemainingCashback(100);
        CashbackRule newRule = new CashbackRule();
        cashbackRuleRepository.save(newRule);
        request.setProgramRule(newRule);
        updateCardController.modifyProgram(request);

        ResponseEntity<?> response = updateCardController.modifyAttributes(request);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Optional<CustomerCard> updatedCard = customerCardRepository.findById(customerCard.getId());
        Assertions.assertTrue(updatedCard.isPresent());
        Assertions.assertTrue(updatedCard.get().isFamily());
        Optional<ProgramData> cashbackDataOpt = updatedCard.get().getProgramsData().stream()
                .filter(program -> program instanceof CashbackData)
                .findFirst();

        Assertions.assertTrue(cashbackDataOpt.isPresent(), "CashbackData not found");
        Assertions.assertEquals(100, ((CashbackData) cashbackDataOpt.get()).getRemainingCashback());
    }


    private CustomerCard createCustomerCard() {
        CustomerCard customerCard = new CustomerCard();
        customerCard.setProgramsData(new LinkedList<>());
        return customerCardRepository.save(customerCard);
    }


}
