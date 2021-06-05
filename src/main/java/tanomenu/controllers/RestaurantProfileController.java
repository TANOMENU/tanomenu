package tanomenu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tanomenu.models.Restaurant;
import tanomenu.repository.RestaurantRepository;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("restaurant/profile")
public class RestaurantProfileController {

    private final RestaurantRepository restaurantRepository;

    public RestaurantProfileController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{uuid}")
    public String show(@PathVariable String uuid, Model model) {
        Optional<Restaurant> restaurant;
        try {
            restaurant = restaurantRepository.find(UUID.fromString(uuid));
        } catch (IllegalArgumentException e) {
            restaurant = Optional.empty();
        }

        return restaurant.map(r -> {
            model.addAttribute("restaurant", r);
            return "/restaurant/profile";
        }).orElse("redirect:/");
    }


}
