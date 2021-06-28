package tanomenu.web.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tanomenu.config.AuthUserDetails;
import tanomenu.core.repository.RestaurantRepository;

@Controller("/")
public class IndexController {

    private final RestaurantRepository restaurantRepository;

    public IndexController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal AuthUserDetails authUserDetails) {
        if (authUserDetails != null)
            return "redirect:/home";

        var featured = restaurantRepository.shuffle(10);
        model.addAttribute("featuredRestaurants", featured);
        return "index";
    }

}
