package tanomenu.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tanomenu.config.AuthUserDetails;
import tanomenu.repository.RestaurantRepository;
import tanomenu.repository.UserRepository;

import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public HomeController(UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal AuthUserDetails userDetails) {
        return new ModelAndView("index", Map.of(
                "currentUser", userRepository.find(userDetails.getUUID()).get(),
                "allUsers", userRepository.findAll(),
                "allRestaurants", restaurantRepository.findAll())
        );
    }

}
