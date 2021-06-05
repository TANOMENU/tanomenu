package tanomenu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tanomenu.repository.RestaurantRepository;

import java.util.UUID;

@Controller
@RequestMapping("restaurant/profile")
public class RestaurantProfileController {

    private final RestaurantRepository restaurantRepository;

    public RestaurantProfileController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{uuid}")
    public String RestaurantProfile(Model model, @PathVariable UUID uuid) {
        var restaurant = restaurantRepository.find(uuid);
        if(restaurant.isPresent()) {
            model.addAttribute("restaurant", restaurant.get());
            return "restaurant/profile";
        }
        return "redirect:/index";
    }


}
