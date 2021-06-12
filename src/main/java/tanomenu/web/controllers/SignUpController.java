package tanomenu.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import tanomenu.core.entity.User;
import tanomenu.core.repository.UserRepository;
import tanomenu.web.dto.UserSignUpDto;

import javax.validation.Valid;

@Controller
public class SignUpController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public SignUpController(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/sign-up")
    @PreAuthorize("isAnonymous()")
    public String signUp(Model model) {
        model.addAttribute("dto", new UserSignUpDto());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    @PreAuthorize("isAnonymous()")
    public String signUp(@ModelAttribute @Valid UserSignUpDto userSignUpDto, BindingResult bindingResult) {
        userRepository.findByEmail(userSignUpDto.getEmail())
                .ifPresent(u -> bindingResult.rejectValue("email", "already.exists", "Email já cadastrado"));

        if (bindingResult.hasErrors())
            return "sign-up";

        var user = modelMapper.map(userSignUpDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/login";
    }

}
