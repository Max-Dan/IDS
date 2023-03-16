package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerRegistrationController {
    @Autowired
    private CustomerRegistrationService customerRegistrationService;
    @Autowired
    private CustomerMapper customerMapper;

    @PutMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer customer = customerMapper.apply(customerRequest);
        boolean registered = customerRegistrationService.registerCustomer(customer);
        if (!registered)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/unregister/{customerId}")
    public ResponseEntity<?> unregisterCustomer(@PathVariable long customerId) {
        customerRegistrationService.unregisterCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
