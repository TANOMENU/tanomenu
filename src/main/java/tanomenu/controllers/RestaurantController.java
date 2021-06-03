package tanomenu.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tanomenu.models.Restaurant;
import tanomenu.repository.RestaurantRepository;

import javax.validation.Valid;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
// TODO Verificar com o grupo as urls do projeto
@RequestMapping("restaurant/")
public class RestaurantController {

    private RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping
    public String setRestaurant(Model model, @RequestBody Restaurant restaurant) {
        model.addAttribute("restaurant", restaurantRepository.save(restaurant));
        return "redirect:login/";
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
    public String getAllRestaurant(Model model, @Valid @PathVariable UUID uuid) {
        var r = restaurantRepository.findAll();
        model.addAttribute(r.stream().filter(restaurant -> restaurant.getUuid().equals(uuid)));
        return "restaurant/"+uuid;
    }
}
