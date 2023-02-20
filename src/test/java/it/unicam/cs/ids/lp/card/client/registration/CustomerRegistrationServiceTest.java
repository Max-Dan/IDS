package it.unicam.cs.ids.lp.card.client.registration;

import it.unicam.cs.ids.lp.card.CustomerCard;
import it.unicam.cs.ids.lp.card.client.Customer;
import it.unicam.cs.ids.lp.card.client.CustomerAccount;
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
                () -> customerRegistrationService.registerCustomer(null, new CustomerAccount(), new CustomerCard()));
        Customer customer = new Customer();
        customer.setName("Steve");
        customer.setSurname("jobs");
        customer.setEmail("StivJobs@gmail.com");
        customer.setTelephoneNumber("132-456-7890");

        CustomerCard customerCard = new CustomerCard();
        //customerCard.setIdentificator("Steve"+"Jobs");????
        customerCard.setTelephoneNumber("132-456-7890");
        customerCard.setEmail("StivJobs@gmail.com");
        customerCard.setProgram(CustomerCard.CardProgram.Points);

        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setName(customer.getName());
        customerAccount.setPassword("SteveIlJobs");
        Assertions.assertTrue(customerRegistrationService.registerCustomer(customer, customerAccount, customerCard));


    }

    @Test
    void isNameValid() {

    }

    @Test
    void isAddressValid() {
    }

    @Test
    void isTelephoneNumberValid() {
    }

    @Test
    void isEmailValid() {
    }
}