package it.unicam.cs.ids.lp.card.client.registration;

import it.unicam.cs.ids.lp.card.CustomerCard;
import it.unicam.cs.ids.lp.card.client.Customer;
import it.unicam.cs.ids.lp.card.client.CustomerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class CustomerRegistrationController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRegistrationService customerRegistrationService;

    @PutMapping("/customerRegistration/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer customer = setCustomer(customerRequest);
        CustomerAccount customerAccount = setCustomerProfile(customerRequest);
        boolean registered = customerRegistrationService.registerCustomer(customer, customerAccount);
        if (registered) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    private CustomerAccount setCustomerProfile(CustomerRequest customerRequest) {
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setName(customerRequest.name());
        customerAccount.setPassword(passwordEncoder.encode(customerRequest.password()));
        customerAccount.setRegistrationDate(LocalDate.now());
        return customerAccount;
    }

    private Customer setCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.name());
        customer.setSurname(customerRequest.surname());
        customer.setTelephoneNumber(customerRequest.telephoneNumber());
        customer.setEmail(customerRequest.email());
        return customer;
    }

    private CustomerCard setCustomerCard(CustomerRequest customerRequest) {
        CustomerCard customerCard = new CustomerCard();
        customerCard.setIdentificator(customerRequest.name() + customerRequest.surname());
        customerCard.setTelephoneNumber(customerRequest.telephoneNumber());
        customerCard.setEmail(customerRequest.email());
        return customerCard;
    }

    @DeleteMapping("/customerUnregistration/{name}")
    public ResponseEntity<?> unregisterActivity(@PathVariable String name) {
        customerRegistrationService.unregisterCustomerByName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected record CustomerRequest(String name, String surname, String telephoneNumber, String email
            , String password) {
    }
}
