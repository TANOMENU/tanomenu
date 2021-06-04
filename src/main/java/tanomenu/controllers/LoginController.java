package tanomenu.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tanomenu.models.User;

@Controller
public class LoginController {

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String show(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

}
