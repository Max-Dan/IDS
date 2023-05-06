package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.card.CardProgram;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customerCard")
public class CustomerCardController {

    private final CustomerCardRepository repository;

    private final CustomerCardMapper customerCardMapper;

    @PostMapping("/createCard")
    public ResponseEntity<CustomerCard> createCustomerCard(@RequestBody CustomerCardRequest request) {
        CustomerCard customerCard = customerCardMapper.apply(request);

        String referred = customerCard.getReferred();
        if (referred != null && !referred.isEmpty()) {
            Optional<CustomerCard> referredCardOpt = repository.findByReferralCode(referred);
            if (referredCardOpt.isPresent()) {
                CustomerCard referredCard = referredCardOpt.get();
                CardProgram program = customerCard.getCard().getProgram();
                switch (program) {
                    case POINTS:
                        referredCard.setPoints(referredCard.getPoints() + 10);
                        customerCard.setPoints(customerCard.getPoints() + 10);
                        break;
                    case LEVELS:
                        referredCard.setTier(referredCard.getTier() + 1);
                        customerCard.setTier(customerCard.getTier() + 1);
                        break;
                    case MEMBERSHIP:
                        referredCard.extendMembership(2);
                        customerCard.extendMembership(2);
                        break;
                    case CASHBACK:
                        referredCard.setRemainingCashback(referredCard.getRemainingCashback() + 5);
                        customerCard.setRemainingCashback(customerCard.getRemainingCashback() + 5);
                        break;
                }
                repository.save(referredCard);
            }
        }
        repository.save(customerCard);
        return new ResponseEntity<>(customerCard, HttpStatus.CREATED);
    }

    @DeleteMapping("/{customerCardId}")
    public ResponseEntity<?> deleteCard(@PathVariable long customerCardId) {
        repository.deleteById(customerCardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
