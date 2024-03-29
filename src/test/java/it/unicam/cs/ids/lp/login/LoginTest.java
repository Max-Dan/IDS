package it.unicam.cs.ids.lp.login;

import it.unicam.cs.ids.lp.admin.Admin;
import it.unicam.cs.ids.lp.admin.AdminRepository;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class LoginTest {

    @Autowired
    private Login login;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private PasswordEncoder passwordEncoder;

    private Admin admin;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @AfterEach
    public void tearDown() {
        if (admin != null) {
            adminRepository.delete(admin);
        }
        if (customer != null) {
            customerRepository.delete(customer);
        }
    }

    @Test
    public void testAdminLogin() {
        admin = new Admin();
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("password"));
        adminRepository.save(admin);

        UserLoginRequest request = new UserLoginRequest();
        request.setEmail("admin@example.com");
        request.setPassword("password");

        ResponseEntity<Boolean> response = login.loginUser(request, null);
        Assertions.assertTrue(response.getBody());
    }

    @Test
    public void testCustomerLogin() {
        customer = new Customer();
        customer.setEmail("customer@example.com");
        customer.setPassword(passwordEncoder.encode("password"));
        customerRepository.save(customer);

        UserLoginRequest request = new UserLoginRequest();
        request.setEmail("customer@example.com");
        request.setPassword("password");

        ResponseEntity<Boolean> response = login.loginUser(request, null);
        Assertions.assertTrue(response.getBody());
    }



}



