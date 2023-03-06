package it.unicam.cs.ids.lp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Login {

    @Autowired
    private PasswordStorage passwordStorage;

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam String username, @RequestParam String password) {
        Password newPassword = new Password();
        newPassword.setUsername(username);
        newPassword.setPassword(password);
        passwordStorage.save(newPassword);
        return "redirect:/login"; // Redirect to login page after saving password
    }

    // other methods
}
