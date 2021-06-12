package tanomenu.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tanomenu.core.entity.Restaurant;
import tanomenu.core.repository.RestaurantRepository;
import tanomenu.core.repository.UserRepository;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

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
    public String index(Model model) {
        var restaurants = restaurantRepository.findAll();
        var restaurantsByCategories = restaurants.stream()
                .filter(r -> r.getCategory() != null)
                .collect(Collectors.groupingBy(r -> r.getCategory().getValue()));
        model.addAttribute("restaurantsByCategories", restaurantsByCategories);
        return "home";
    }

}
