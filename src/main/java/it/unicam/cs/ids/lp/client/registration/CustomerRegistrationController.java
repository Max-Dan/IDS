package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerRegistrationController {

    private final CustomerRegistrationService customerRegistrationService;

    private final CustomerMapper customerMapper;

    private final CustomerRepository customerRepository;

    @PutMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer customer = customerMapper.apply(customerRequest);
        return customerRegistrationService.register(customer)
                .map(customer1 -> new ResponseEntity<>(HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/unregister/{customerId}")
    public ResponseEntity<?> unregisterCustomer(@PathVariable long customerId) {
        customerRegistrationService.unregister(customerRepository.findById(customerId).orElseThrow());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
