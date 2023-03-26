package it.unicam.cs.ids.lp.login;

import it.unicam.cs.ids.lp.admin.Admin;
import it.unicam.cs.ids.lp.client.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Login {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
        Object user = userService.findByEmail(email);
        if (user instanceof Admin admin) {
            if (passwordEncoder.matches(password, admin.getPassword())) {
                return "redirect:/admin/home";
            }
        } else if (user instanceof Customer customer) {
            if (passwordEncoder.matches(password, customer.getPassword())) {
                return "redirect:/customer/home";
            }
        }
        model.addAttribute("error", "Invalid email or password");
        return "login";
    }

}

