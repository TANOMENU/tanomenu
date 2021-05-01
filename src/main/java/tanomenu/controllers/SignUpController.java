package tanomenu.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder bCryptPasswordEncoder;

    public SignUpController(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/sign-up")
    @PreAuthorize("isAnonymous()")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "sign-up";

    }

    @PostMapping("/sign-up")
    @PreAuthorize("isAnonymous()")
    public String signUp(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> bindingResult.rejectValue("email",
                        "email.already.exists", "Email já cadastrado"));

        if(bindingResult.hasErrors())
            return "sign-up";

        userRepository.save(user.toBuilder().password(bCryptPasswordEncoder.encode(user.getPassword())).build());
        return "redirect:/users";
    }

}
