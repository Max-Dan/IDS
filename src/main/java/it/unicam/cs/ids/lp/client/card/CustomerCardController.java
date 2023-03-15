package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.card.CustomerCard.CardProgram;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customerCard")
public class CustomerCardController {

    private final CustomerCardRepository repository;

    @Autowired
    public CustomerCardController(CustomerCardRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/createCard")
    public ResponseEntity<CustomerCard> createCustomerCard(@RequestBody CustomerCardRequest request) {
        CustomerCard customerCard = new CustomerCard();
        customerCard.setCustomer(request.toCustomer());
        customerCard.setProgram(request.getProgram());
        customerCard.setFamily(request.isFamily());
        repository.save(customerCard);
        return new ResponseEntity<>(customerCard, HttpStatus.CREATED);
    }

    @DeleteMapping("/{customerName}/{program}")
    public ResponseEntity<?> deleteCard(@PathVariable String customerName, @PathVariable CardProgram program) {
        CustomerCard customerCard = repository.findByCustomerNameAndProgram(customerName, program);
        if (customerCard == null) {
            return new ResponseEntity<>("Customer card not found", HttpStatus.NOT_FOUND);
        }
        repository.delete(customerCard);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerCardRequest {
        private String customerName;
        private CardProgram program;
        private boolean family;

        public Customer toCustomer() {
            Customer customer = new Customer();
            customer.setName(customerName);
            return customer;
        }
    }
}