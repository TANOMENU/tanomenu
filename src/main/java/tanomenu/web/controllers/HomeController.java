package tanomenu.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tanomenu.core.repository.RestaurantRepository;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final RestaurantRepository restaurantRepository;

    public HomeController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    public String home(Model model) {
        var restaurants = restaurantRepository.findAll();
        var restaurantsByCategories = restaurants.stream()
                .filter(r -> r.getCategory() != null)
                .collect(Collectors.groupingBy(r -> r.getCategory().getValue()));
        model.addAttribute("restaurantsByCategories", restaurantsByCategories);
        return "home";
    }

}
