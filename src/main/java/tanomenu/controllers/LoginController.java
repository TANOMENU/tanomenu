package tanomenu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tanomenu.models.User;
import tanomenu.repository.UserRepository;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TODO Pq esta signUp?
    @GetMapping("/login")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

}
