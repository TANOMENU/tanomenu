package tanomenu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import tanomenu.models.User;
import tanomenu.repository.UserRepository;

import javax.validation.Valid;

@Controller
public class SignUpController {

    private final UserRepository userRepository;

    public SignUpController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "sign-up";

    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "sign-up";

        userRepository.save(user);
        return "redirect:/users";
    }

}
