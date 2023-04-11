package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.client.registration.CustomerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerRepository customerRepository;

    private final CustomerService customerService;

    public CustomerController(CustomerRepository customerRepository,
                              CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @GetMapping("/getData/{customerId}")
    public ResponseEntity<?> getCustomerData(@PathVariable long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok().body(customer.get());
    }

    @PostMapping("/modifyData/{customerId}")
    public ResponseEntity<?> modifyData(@PathVariable long customerId, @RequestBody CustomerRequest customerRequest) {
        customerService.modifyCustomerData(customerId, customerRequest);
        return ResponseEntity.ok("");
    }

    @GetMapping("{customerId}/subscribeToCampaign/{campaignId}")
    public ResponseEntity<?> subscribeToCampaign(@PathVariable long customerId, @PathVariable long campaignId) {
        boolean success = customerService.subscribeToCampaign(customerId, campaignId);
        return success ? ResponseEntity.ok("")
                : ResponseEntity.badRequest().body("Il customer non ha la carta della campagna");
    }
}
