package tanomenu.controllers;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tanomenu.repository.UserRepository;

import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("profile");
    }

    @PutMapping
    public void updateProfile(@RequestBody Map<String, Object> requestBody) {
        System.out.println(requestBody);
    }

    @PutMapping("/security")
    public void updateSecurity(@RequestBody Map<String, Object> requestBody) {
        System.out.println(requestBody);
    }
}
