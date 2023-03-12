package it.unicam.cs.ids.lp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Login {

    @Autowired
    private PasswordStorage passwordStorage;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        Password savedPassword = passwordStorage.findByUsername(username);
        if (savedPassword != null && savedPassword.getPassword().equals(password)) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam String username, @RequestParam String password) {
        Password newPassword = new Password();
        newPassword.setUsername(username);
        newPassword.setPassword(password);
        passwordStorage.save(newPassword);
        return "redirect:/login";
    }

    // other methods
}