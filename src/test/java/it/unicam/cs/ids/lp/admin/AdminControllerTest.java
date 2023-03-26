package it.unicam.cs.ids.lp.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.lp.LoyaltyPlatformApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig(LoyaltyPlatformApplication.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class AdminControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminMapper adminMapper;
    private Admin admin;

    private ResultActions mvcPut(AdminRequest adminRequest) throws Exception {
        return mvc.perform(put("/admin/register")
                .content(objectMapper.writeValueAsString(adminRequest))
                .contentType(MediaType.APPLICATION_JSON)
        );
    }

    private ResultActions mvcDelete(long adminId) throws Exception {
        return mvc.perform(delete("/admin/unregister/" + adminId));
    }

    @BeforeEach
    void setUp() {
        Admin admin = adminMapper.apply(new AdminRequest(
                "admin@example.com",
                "Admin123"
        ));
        this.admin = adminRepository.save(admin);
    }

    @Test
    void registerAdmin() throws Exception {
        String newEmail = "newadmin@example.com";
        mvcPut(
                new AdminRequest(
                        newEmail,
                        "NewAdmin123"
                )
        ).andExpect(status().isCreated());
        Assertions.assertTrue(adminRepository.existsByEmail(newEmail));
    }

    @Test
    void unregisterAdmin() throws Exception {
        long adminId = admin.getId();
        mvcDelete(adminId).andExpect(status().isOk());
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        Assertions.assertTrue(optionalAdmin.isEmpty());
    }
}
