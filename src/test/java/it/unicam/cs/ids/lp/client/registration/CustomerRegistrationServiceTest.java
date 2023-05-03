package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LoyaltyPlatformApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CustomerRegistrationServiceTest {

    @Autowired
    private CustomerRegistrationService customerRegistrationService;
    @Autowired
    private CustomerMapper customerMapper;

    private Customer customer;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        CustomerRequest customerRequest =
                new CustomerRequest("Steve", "Jobs", "132-456-7890", "SteveJobs@gmail.com", "iLoveApple123");
        customer = customerMapper.apply(customerRequest);
    }

    @Test
    void registerCustomerTest() {
        assertThrows(NullPointerException.class,
                () -> customerRegistrationService.registerCustomer(null));
        Assertions.assertTrue(customerRegistrationService.registerCustomer(customer));
        Assertions.assertTrue(customerRepository.existsById(customer.getId()));
        Assertions.assertFalse(customerRegistrationService.registerCustomer(customer));
    }

    @Test
    public void unregisterCustomerTest() {
        Assertions.assertTrue(customerRegistrationService.registerCustomer(customer));

        customerRegistrationService.unregisterCustomer(customer.getId());
        Assertions.assertFalse(customerRepository.existsById(customer.getId()));
    }
}

