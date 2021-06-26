package tanomenu.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tanomenu.config.AuthUserDetails;

import tanomenu.core.repository.UserRepository;
import tanomenu.core.storage.StorageService;
import tanomenu.web.dto.UserEditDto;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final StorageService storageService;

    public ProfileController(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper, StorageService storageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.storageService = storageService;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal AuthUserDetails userDetails, Model model) {
        var user = userRepository.find(userDetails.getUUID());

        return user.map(u -> {
            var userEditDto = modelMapper.map(u, UserEditDto.class);
            userEditDto.setPassword(null);

            model.addAttribute("user", u);
            model.addAttribute("userEditDto", userEditDto);
            return "profile-user";
        }).orElseThrow();
    }

    @PostMapping
    public String save(@AuthenticationPrincipal AuthUserDetails userDetails, @ModelAttribute UserEditDto userEditDto, BindingResult bindingResult, Model model) {
        var user = userRepository.find(userDetails.getUUID()).get();

        if (!passwordEncoder.matches(userEditDto.getConfirmPassword(), user.getPassword()))
            bindingResult.rejectValue("confirmPassword", "invalid.password", "Senha inválida");

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "profile-user";
        }

        var password = userEditDto.getPassword().isEmpty()
                ? null
                : passwordEncoder.encode(userEditDto.getPassword());
        userEditDto.setPassword(password);

        modelMapper.map(userEditDto, user);

        if(!userEditDto.getImage().isEmpty()) {
            var image = (user.getImage() != null)
                    ? storageService.update(user.getImage(), userEditDto.getImage())
                    : storageService.save(userEditDto.getImage());
            user.setImage(image);
        }

        userRepository.update(user.getUuid(), user);
        return "redirect:/profile";
    }

}
