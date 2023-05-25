package it.unicam.cs.ids.lp.client.card;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customerCard")
public class UpdateCardController {

    private final CustomerCardRepository customerCardRepository;

    private final CustomerCardController customerCardController;
    @PutMapping("/modifyProgram")
    public ResponseEntity<?> modifyProgram(@RequestBody CustomerCardUpdateRequest request) {
        Optional<CustomerCard> optionalCard = customerCardRepository.findById(request.getCustomerCardId());

        if (optionalCard.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerCard customerCard = optionalCard.get();
        customerCardController.deleteCard(customerCard.getId());
        CustomerCardRequest customerCardRequest =
                new CustomerCardRequest(
                        customerCard.getCustomer().getId(),
                        customerCard.getCard().getId(),
                        false,
                        null
                );
        customerCardController.createCustomerCard(customerCardRequest);

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

        customerCard.setFamily(request.isFamily());

        customerCardRepository.save(customerCard);
        System.out.println("Card attributes modified: " + customerCard);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
