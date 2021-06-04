package tanomenu.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tanomenu.config.AuthUserDetails;
import tanomenu.models.Restaurant;
import tanomenu.repository.RestaurantRepository;

import javax.validation.Valid;
import java.util.UUID;

@Controller
// TODO Verificar com o grupo as urls do projeto
@RequestMapping("restaurant/")
public class RestaurantController {

    private RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping
    public String setRestaurant(Model model, @ModelAttribute Restaurant restaurant, @AuthenticationPrincipal AuthUserDetails userDetails) {
        var r = restaurantRepository.save(restaurant);
        model.addAttribute("restaurant", r);
        return "redirect:profile/" + r.getUuid();
    }

    @DeleteMapping("{uuid}")
    public String deleteRestaurant(@PathVariable UUID uuid) {
        restaurantRepository.delete(uuid);
        return "home/";
    }

    @GetMapping("{uuid}")
    public String getRestaurant(Model model, @Valid @PathVariable UUID uuid) {
        model.addAttribute("restaurant", restaurantRepository.find(uuid));
        return "redirect:restaurant/"+uuid+"/";
    }

    @GetMapping
    public String getAllRestaurant(Model model, @AuthenticationPrincipal AuthUserDetails userDetails) {
        var r = restaurantRepository.findAll();
        model.addAttribute(r.stream().filter(restaurant -> restaurant.getUserUuid().equals(userDetails.getUUID())));
        return "restaurant/" + userDetails.getUUID();
    }

    @GetMapping("create")
    public String Create(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "Restaurant/save-restaurant";
    }
}
