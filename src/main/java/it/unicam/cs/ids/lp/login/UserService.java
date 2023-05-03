package it.unicam.cs.ids.lp.login;

import it.unicam.cs.ids.lp.admin.AdminRepository;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    @Qualifier("adminRepository")
    private AdminRepository adminRepository;

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository customerRepository;

    public Optional<Object> findByEmail(String email) {
        Optional<Object> admin = adminRepository.findByEmail(email).map(adminObj -> adminObj);
        if (admin.isPresent()) {
            return admin;
        }
        return customerRepository.findByEmail(email).map(customer -> customer);
    }
}



