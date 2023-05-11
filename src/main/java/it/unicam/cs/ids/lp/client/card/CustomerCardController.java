package it.unicam.cs.ids.lp.client.card;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customerCard")
public class CustomerCardController {

    private final CustomerCardRepository repository;

    private final CustomerCardMapper customerCardMapper;

    @PostMapping("/createCard")
    public ResponseEntity<CustomerCard> createCustomerCard(@RequestBody CustomerCardRequest request) {
        CustomerCard customerCard = customerCardMapper.apply(request);

        repository.save(customerCard);
        return new ResponseEntity<>(customerCard, HttpStatus.CREATED);
    }

    @DeleteMapping("/{customerCardId}")
    public ResponseEntity<?> deleteCard(@PathVariable long customerCardId) {
        repository.deleteById(customerCardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

