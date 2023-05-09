package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.util.DataValidator;
import it.unicam.cs.ids.lp.util.DataValidatorUtil;
import it.unicam.cs.ids.lp.util.Registry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerRegistrationService
        implements Registry<Customer>, DataValidator<Customer> {

    private final CustomerRepository customerRepository;

    private final DataValidatorUtil dataValidator;

    public boolean areRegistrationValuesValid(Customer customer) {
        return !dataValidator.isNameValid(customer.getName())
                || !dataValidator.isSurnameValid(customer.getSurname())
                || !dataValidator.isTelephoneNumberValid(customer.getTelephoneNumber())
                || !dataValidator.isEmailValid(customer.getEmail());
    }

    @Override
    public Optional<Customer> register(Customer customer) {
        Objects.requireNonNull(customer);
        if (customerRepository.existsByEmail(customer.getEmail())
                || areRegistrationValuesValid(customer))
            return Optional.empty();
        customerRepository.save(customer);
        return Optional.of(customer);
    }

    @Override
    public void unregister(Customer customer) {
        customerRepository.delete(customer);
    }
}
