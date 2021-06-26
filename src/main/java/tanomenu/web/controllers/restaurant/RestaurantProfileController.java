package tanomenu.web.controllers.restaurant;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tanomenu.config.AuthUserDetails;
import tanomenu.core.entity.Restaurant;
import tanomenu.core.entity.User;
import tanomenu.core.repository.RestaurantRepository;
import tanomenu.core.repository.UserRepository;
import tanomenu.core.storage.StorageService;
import tanomenu.web.dto.RestaurantRegisterDto;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("restaurant/profile")
public class RestaurantProfileController {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final StorageService storageService;
    private final ModelMapper modelMapper;

    public RestaurantProfileController(RestaurantRepository restaurantRepository, UserRepository userRepository, StorageService storageService, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.storageService = storageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{uuid}")
    public String show(@PathVariable String uuid, Model model, @AuthenticationPrincipal AuthUserDetails userDetails) {
        Optional<Restaurant> restaurant;
        var restaurants = restaurantRepository.findByOwner(userDetails.getUUID());
        var user = userRepository.find(userDetails.getUUID());
        try {
            restaurant = restaurantRepository.find(UUID.fromString(uuid));
        } catch (IllegalArgumentException e) {
            restaurant = Optional.empty();
        }

        return restaurant.map(r -> {
            if(!r.getUserUuid().equals(userDetails.getUUID())) {
                return "redirect:/";
            }
            model.addAttribute("restaurant", r);
            model.addAttribute("restaurants", restaurants);
            model.addAttribute("user", user.get());
            return "/restaurant/profile";
        }).orElse("redirect:/");
    }

    @GetMapping("/edit/{uuid}")
    public String edit(@AuthenticationPrincipal AuthUserDetails userDetails, Model model, @PathVariable UUID uuid) {
        Optional<Restaurant> restaurant = restaurantRepository.find(uuid);
        var user = userRepository.find(userDetails.getUUID());
        var restaurants = restaurantRepository.findByOwner(userDetails.getUUID());
        return restaurant.map(r -> {
            if(!r.getUserUuid().equals(user.get().getUuid())) {
                return "redirect:/";
            }
            model.addAttribute("restaurant", r);
            model.addAttribute("user", user.get());
            model.addAttribute("restaurants", restaurants);
            return "restaurant/edit";
        }).orElse(
                "redirect:/"
        );

    }

    @PostMapping("/edit/{uuid}")
    public String update(@AuthenticationPrincipal AuthUserDetails userDetails, Model model, @ModelAttribute RestaurantRegisterDto restaurantRegisterDto, @PathVariable UUID uuid) {
        Optional<Restaurant> restaurant = restaurantRepository.find(uuid);

        return restaurant.map(r -> {
            if(!r.getUserUuid().equals(userDetails.getUUID()))
                return "redirect:/";

            modelMapper.map(restaurantRegisterDto, r);
            if(!restaurantRegisterDto.getImage().isEmpty()) {
                var image = storageService.update(r.getImage(), restaurantRegisterDto.getImage());
                r.setImage(image);
            }
            restaurantRepository.update(uuid, restaurant.get());
            model.addAttribute("restaurant", restaurant);
            return "redirect:/restaurant/profile/edit/" + r.getUuid();
        }).orElse(
                "redirect:/"
        );

    }
}
