package it.unicam.cs.ids.lp.client;

import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import it.unicam.cs.ids.lp.activity.campaign.CampaignRepository;
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
    @Autowired
    private CampaignRepository campaignRepository;

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

    @GetMapping("{customerId}/subscribeToCampaign/{campaignId}")
    public ResponseEntity<?> subscribeToCampaign(@PathVariable long customerId, @PathVariable long campaignId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        if (customer.getCards()
                .stream()
                .noneMatch(customerCard -> customerCard.getCard().equals(campaign.getCard())))
            return ResponseEntity.badRequest()
                    .body("Il customer non ha la carta della campagna");
        return ResponseEntity.ok("");
    }
}
