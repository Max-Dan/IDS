package it.unicam.cs.ids.lp.login;

import it.unicam.cs.ids.lp.admin.Admin;
import it.unicam.cs.ids.lp.client.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
public class Login {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/loginshow")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> loginUser(@RequestBody UserLoginRequest userLoginRequest, Model model) {
        String email = userLoginRequest.getEmail();
        String password = userLoginRequest.getPassword();

        Optional<Object> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            Object user = userOptional.get();
            if (user instanceof Admin admin) {
                if (passwordEncoder.matches(password, admin.getPassword())) {
                    return ResponseEntity.ok(true);
                }
            } else if (user instanceof Customer customer) {
                if (passwordEncoder.matches(password, customer.getPassword())) {
                    return ResponseEntity.ok(true);
                }
            }
        }

        return ResponseEntity.ok(false);
    }



}
