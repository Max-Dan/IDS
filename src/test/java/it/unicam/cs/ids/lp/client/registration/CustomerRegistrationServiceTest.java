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

    @Test
    void registerCustomer() {
        assertThrows(NullPointerException.class,
                () -> customerRegistrationService.registerCustomer(null));
        Customer customer = new Customer();
        customer.setEmail("customer.email@test.com");
        Assertions.assertTrue(customerRegistrationService.registerCustomer(customer));
        Assertions.assertFalse(customerRegistrationService.registerCustomer(customer));
    }
}
