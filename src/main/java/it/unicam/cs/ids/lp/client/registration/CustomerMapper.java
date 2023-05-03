package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CustomerMapper implements Function<CustomerRequest, Customer> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Customer apply(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.name());
        customer.setSurname(customerRequest.surname());
        customer.setTelephoneNumber(customerRequest.telephoneNumber());
        customer.setEmail(customerRequest.email());
        customer.setPassword(passwordEncoder.encode(customerRequest.password()));
        customer.setRegistrationDate(LocalDate.now());
        return customer;
    }
}
