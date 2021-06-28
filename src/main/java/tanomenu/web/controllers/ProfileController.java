package tanomenu.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tanomenu.config.AuthUserDetails;

import tanomenu.core.entity.Restaurant;
import tanomenu.core.entity.restaurant.Product;
import tanomenu.core.repository.ProductRepository;
import tanomenu.core.repository.RestaurantRepository;
import tanomenu.core.repository.UserRepository;
import tanomenu.core.storage.StorageService;
import tanomenu.web.dto.UserEditDto;

import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final StorageService storageService;
    private final RestaurantRepository restaurantRepository;
    private final ProductRepository productRepository;

    public ProfileController(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper, StorageService storageService, RestaurantRepository restaurantRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.storageService = storageService;
        this.restaurantRepository = restaurantRepository;
        this.productRepository = productRepository;
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

    @GetMapping("/user/delete")
    public String delete(@AuthenticationPrincipal AuthUserDetails userDetails) {
        var restaurants = restaurantRepository.findByOwner(userDetails.getUUID());
        for (Restaurant restaurant: restaurants) {
            var products = productRepository.findByRestaurant(restaurant.getUuid());

            for (Product product : products) {
                if(product.getImage() != null)
                    storageService.delete(product.getImage());

                productRepository.delete(product.getUuid());
            }
            if(restaurant.getImage() != null)
                storageService.delete(restaurant.getImage());

            restaurantRepository.delete(restaurant.getUuid());

        }
        var user = userRepository.find(userDetails.getUUID()).get();

        if(user.getImage() != null)
            storageService.delete(user.getImage());

        userRepository.delete(userDetails.getUUID());

        return "redirect:/logout";
    }

}
