package tanomenu.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tanomenu.config.AuthUserDetails;
import tanomenu.models.User;
import tanomenu.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public ProfileController(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal AuthUserDetails userDetails, Model model) {
        Optional<User> user = userRepository.find(userDetails.getUUID());
        user.ifPresent(u -> model.addAttribute("user", u));
        return new ModelAndView("profile");
    }

    @PostMapping
    public void updateProfile(@AuthenticationPrincipal AuthUserDetails userDetails, @ModelAttribute User updatedUser) {
        userRepository.update(userDetails.getUUID(), updatedUser);
    }

    @PostMapping("/security")
    public String updateSecurity(@AuthenticationPrincipal AuthUserDetails userDetails, @ModelAttribute User updatedUser, HttpServletRequest request) {
        userRepository.update(userDetails.getUUID(), updatedUser.toBuilder().password(bCryptPasswordEncoder.encode(updatedUser.getPassword())).build());
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
