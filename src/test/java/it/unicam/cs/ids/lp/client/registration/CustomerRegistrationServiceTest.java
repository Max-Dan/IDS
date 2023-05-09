package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerRegistrationServiceTest {

    @Autowired
    private CustomerRegistrationService customerRegistrationService;
    @Autowired
    private CustomerMapper customerMapper;
    private final List<Customer> customers = new ArrayList<>();
    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void tearDown() {
        customerRepository.deleteAll(customers);
        customers.clear();
    }

    @Test
    void registerCustomer() {
        assertThrows(NullPointerException.class,
                () -> customerRegistrationService.register(null));
        CustomerRequest customerRequest =
                new CustomerRequest("Steve", "Jobs", "132-456-7890", "SteveJobs@gmail.com", "iLoveApple123");
        Customer customer = customerMapper.apply(customerRequest);
        Assertions.assertTrue(customerRegistrationService.register(customer).isPresent());
        Assertions.assertFalse(customerRegistrationService.register(customer).isPresent());
        customers.add(customer);

        CustomerRequest anotherCustomerRequest =
                new CustomerRequest("Tim", "Cook", "123-456-7890", "TimCook@gmail.com", "iLoveAppleToo456");
        Customer anotherCustomer = customerMapper.apply(anotherCustomerRequest);
        Assertions.assertTrue(customerRegistrationService.register(anotherCustomer).isPresent());
        customers.add(anotherCustomer);
    }
}


