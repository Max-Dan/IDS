package it.unicam.cs.ids.lp.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import it.unicam.cs.ids.lp.client.registration.CustomerMapper;
import it.unicam.cs.ids.lp.client.registration.CustomerRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = LoyaltyPlatformApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    private Customer customer;

    public CustomerControllerTest() {

        this.customer = null;
    }

    private ResultActions mvcPost(CustomerRequest customerRequest) throws Exception {
        return mvc.perform(post("/customer/modifyData/" + customer.getId())
                .content(objectMapper.writeValueAsString(customerRequest))
                .contentType(MediaType.APPLICATION_JSON)
        );
    }

    @BeforeAll
    void setUp() {
        Customer customer = customerMapper.apply(new CustomerRequest(
                "gianni",
                "morandi",
                "334-456-2345",
                "gianni.morandi@gmail.com",
                "Prendi il latte"
        ));
        this.customer = customerRepository.save(customer);
    }

    @Test
    void modifyDataName() throws Exception {
        String newName = "Massimo";
        mvcPost(
                new CustomerRequest(
                        newName,
                        null,
                        null,
                        null,
                        null
                )
        ).andExpect(status().isOk());
        Assertions.assertEquals(
                customerRepository.findById(customer.getId()).orElseThrow().getName(),
                newName);
    }

    @Test
    void modifyDataSurname() throws Exception {
        String newSurname = "Ranieri";
        mvcPost(
                new CustomerRequest(
                        null,
                        newSurname,
                        null,
                        null,
                        null
                )
        ).andExpect(status().isOk());
        Assertions.assertEquals(
                customerRepository.findById(customer.getId()).orElseThrow().getSurname(),
                newSurname);
    }

    @Test
    void modifyDataTelephone() throws Exception {
        String newTel = "445-678-6548";
        mvcPost(
                new CustomerRequest(
                        null,
                        null,
                        newTel,
                        null,
                        null
                )
        ).andExpect(status().isOk());
        Assertions.assertEquals(
                customerRepository.findById(customer.getId()).orElseThrow().getTelephoneNumber(),
                newTel);
    }

    @Test
    void modifyDataEmail() throws Exception {
        String newEmail = "massimo.ranieri@gmail.com";
        mvcPost(
                new CustomerRequest(
                        null,
                        null,
                        null,
                        newEmail,
                        null
                )
        ).andExpect(status().isOk());
        Assertions.assertEquals(
                customerRepository.findById(customer.getId()).orElseThrow().getEmail(),
                newEmail);
    }
}
