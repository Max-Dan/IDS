package it.unicam.cs.ids.lp.login;

import it.unicam.cs.ids.lp.admin.AdminRepository;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Object findByEmail(String email) {
        return adminRepository.findByEmail(email)
                .map(admin -> (Object) admin)
                .orElse(customerRepository.findByEmail(email)
                        .map(customer -> (Object) customer)
                        .orElse(null));
    }
}



