package tanomenu.web.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tanomenu.config.AuthUserDetails;
import tanomenu.core.entity.Restaurant;
import tanomenu.core.entity.User;
import tanomenu.core.repository.RestaurantRepository;
import tanomenu.core.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/settings")
public class settingsController {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public settingsController(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @ModelAttribute("restaurants")
    public List<Restaurant> restaurants(@AuthenticationPrincipal AuthUserDetails userDetails) {
        return restaurantRepository.findByOwner(userDetails.getUUID());
    }

    @ModelAttribute("user")
    public User user(@AuthenticationPrincipal AuthUserDetails userDetails) {
        return userRepository.find(userDetails.getUUID()).get();
    }

    @GetMapping("/user")
    public String show() {
        return "settings";
    }

    @GetMapping("/restaurant/{uuid}")
    public String show(@PathVariable String uuid, Model model) {
        Optional<Restaurant> restaurant;

        try {
            restaurant = restaurantRepository.find(UUID.fromString(uuid));
        } catch (IllegalArgumentException e) {
            restaurant = Optional.empty();
        }

        return restaurant.map(r -> {
            model.addAttribute("selectedRestaurant", r);
            return "settings";
        }).orElse("/");
    }
}
