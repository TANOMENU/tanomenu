package tanomenu.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tanomenu.models.Restaurant;
import tanomenu.repository.RestaurantRepository;

import javax.validation.Valid;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
// TODO Verificar com o grupo as urls do projeto
@RequestMapping("users/{uuid}/restaurant")
public class RestaurantController {

    private RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping
    public ResponseEntity<Restaurant> setRestaurant(@RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantRepository.save(restaurant));
    }

    @DeleteMapping("{uuid}")
    public void deleteRestaurant(@PathVariable UUID uuid) {
        restaurantRepository.delete(uuid);
    }

    @GetMapping("{uuid}")
    public ResponseEntity<Restaurant> getRestaurant(@Valid @PathVariable UUID uuid) {
        return ResponseEntity.of(restaurantRepository.find(uuid));
    }

    @GetMapping
    public ResponseEntity<Stream<Restaurant>> getAllRestaurant(@Valid @PathVariable UUID uuid) {
        var r = restaurantRepository.findAll();
        return ResponseEntity.ok(r.stream().filter(restaurant -> restaurant.getUser().getUuid().equals(uuid)));
    }
}
