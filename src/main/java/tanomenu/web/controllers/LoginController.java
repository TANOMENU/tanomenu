package tanomenu.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tanomenu.web.dto.UserLoginDto;

@Controller
public class LoginController {

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String show(Model model) {
        model.addAttribute("userLoginDto", new UserLoginDto());
        return "login";
    }

}
