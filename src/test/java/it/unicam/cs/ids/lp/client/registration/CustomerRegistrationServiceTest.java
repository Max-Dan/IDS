package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerRegistrationServiceTest {

    @Autowired
    private CustomerRegistrationService customerRegistrationService;
    @Autowired
    private CustomerMapper customerMapper;

    @Test
    void registerCustomer() {
        assertThrows(NullPointerException.class,
                () -> customerRegistrationService.registerCustomer(null));
        CustomerRequest customerRequest =
                new CustomerRequest("Steve", "jobs", "StivJobs@gmail.com", "132-456-7890", "SteveIlJobs", "");
        Customer customer = customerMapper.apply(customerRequest);
        Assertions.assertTrue(customerRegistrationService.registerCustomer(customer));
        Assertions.assertFalse(customerRegistrationService.registerCustomer(customer));
    }
}
