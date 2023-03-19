package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Function;

@Service
public class CustomerMapper implements Function<CustomerRequest, Customer> {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Customer apply(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.name());
        customer.setSurname(customerRequest.surname());
        customer.setTelephoneNumber(customerRequest.telephoneNumber());
        customer.setEmail(customerRequest.email());
        customer.setPassword(passwordEncoder.encode(customerRequest.password()));
        customer.setRegistrationDate(LocalDate.now());
        customer.setReferred(customerRequest.referralCode());
        return customer;
    }
}
