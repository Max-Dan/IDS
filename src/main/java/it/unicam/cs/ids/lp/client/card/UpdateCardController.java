package it.unicam.cs.ids.lp.client.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/customerCard")
public class UpdateCardController {

    @Autowired
    private CustomerCardRepository customerCardRepository;

    @PutMapping("/modifyProgram")
    public ResponseEntity<?> modifyProgram(@RequestBody CustomerCardUpdateRequest request) {
        Optional<CustomerCard> optionalCard = customerCardRepository.findById(request.getCustomerCardId());

        if (optionalCard.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerCard customerCard = optionalCard.get();
        customerCard.setProgram(request.getNewProgram());
        customerCardRepository.save(customerCard);

        System.out.println("Card program modified: " + customerCard);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/modifyAttributes")
    public ResponseEntity<?> modifyAttributes(@RequestBody CustomerCardUpdateRequest request) {
        Optional<CustomerCard> optionalCard = customerCardRepository.findById(request.getCustomerCardId());

        if (optionalCard.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerCard customerCard = optionalCard.get();
        customerCard.setPoints(request.getPoints());
        customerCard.setTier(request.getTier());
        customerCard.setRemainingCashback(request.getRemainingCashback());
        customerCard.setMembership(request.getMembership());
        customerCard.setFamily(request.isFamily());
        customerCardRepository.save(customerCard);

        System.out.println("Card attributes modified: " + customerCard);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
