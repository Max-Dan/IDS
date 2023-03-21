package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.client.registration.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/getData/{customerId}")
    public ResponseEntity<?> getCustomerData(@PathVariable long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok().body(customer.get());
    }

    @PostMapping("/modifyData/{customerId}")
    public ResponseEntity<?> modifyData(@PathVariable long customerId, @RequestBody CustomerRequest customerRequest) {
        Customer customer = customerRepository.getReferenceById(customerId);
        if (customerRequest.name() != null)
            customer.setName(customerRequest.name());
        if (customerRequest.surname() != null)
            customer.setSurname(customerRequest.surname());
        if (customerRequest.telephoneNumber() != null)
            customer.setTelephoneNumber(customerRequest.telephoneNumber());
        if (customerRequest.email() != null)
            customer.setEmail(customerRequest.email());
        return ResponseEntity.ok("");
    }
}
