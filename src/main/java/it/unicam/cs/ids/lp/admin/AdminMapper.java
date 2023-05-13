package it.unicam.cs.ids.lp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Function;

@Service
public class AdminMapper implements Function<AdminRequest, Admin> {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Admin apply(AdminRequest adminRequest) {
        Admin admin = new Admin();
        admin.setEmail(adminRequest.email());
        admin.setPassword(passwordEncoder.encode(adminRequest.password()));
        admin.setRegistrationDate(LocalDate.now());
        return admin;
    }
}
