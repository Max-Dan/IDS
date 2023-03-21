package it.unicam.cs.ids.lp.client.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customerCard")
public class CustomerCardController {

    private final CustomerCardRepository repository;
    private final CustomerCardMapper customerCardMapper;

    @Autowired
    public CustomerCardController(CustomerCardRepository repository, CustomerCardMapper customerCardMapper) {
        this.repository = repository;
        this.customerCardMapper = customerCardMapper;
    }

    @PostMapping("/createCard")
    public ResponseEntity<CustomerCard> createCustomerCard(@RequestBody CustomerCardRequest request) {
        CustomerCard customerCard = customerCardMapper.apply(request);
        // Imposta il campo del link di riferimento utilizzato
        String referred = customerCard.getReferred();
        if (referred != null && !referred.isEmpty()) {
            Optional<CustomerCard> referredCardOpt = repository.findByReferralCode(referred);
        }
        // TODO applicare una regola per chi a fatto il referral e chi ha ricevuto
        // Salva la CustomerActivityCard aggiornata
        repository.save(customerCard);
        return new ResponseEntity<>(customerCard, HttpStatus.CREATED);
    }

    @DeleteMapping("/{customerCardId}")
    public ResponseEntity<?> deleteCard(@PathVariable long customerCardId) {
        repository.deleteById(customerCardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
