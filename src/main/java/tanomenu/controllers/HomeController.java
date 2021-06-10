package tanomenu.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tanomenu.config.AuthUserDetails;
import tanomenu.repository.RestaurantRepository;
import tanomenu.repository.UserRepository;

import java.util.LinkedHashMap;
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
    public String index(Model model) {
        var restaurants = restaurantRepository.findAll();
        var restaurantsByCategories = new LinkedHashMap<>() {{
            put("Seus Favoritos", restaurants);
            put("Mais Bem Avaliados", restaurants);
            put("Churrascarias", restaurants);
            put("Você também pode gostar", restaurants);
            put("Saladas", restaurants);
        }};
        model.addAttribute("restaurantsByCategories", restaurantsByCategories);
        return "home";
    }

}
