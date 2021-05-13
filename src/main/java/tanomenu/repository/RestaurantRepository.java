package tanomenu.repository;

import org.springframework.stereotype.Component;
import tanomenu.models.Restaurant;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RestaurantRepository {

    private final List<Restaurant> restaurants = Collections.synchronizedList(new ArrayList<>());

    // TODO verificar se aqui salvaremos uma lista de restaurantes separada, ou se salvaremos essa lista no usuário
    public Restaurant save(Restaurant restaurant) {
        var r = restaurant.withUuid(UUID.randomUUID());
        restaurants.add(r);
        return r.toBuilder().build();
    }

    // TODO de acordo de como salvaremos a lista de restaurantes, aqui mudará o update
    public Restaurant update(UUID uuid, Restaurant restaurant) {
        var response = restaurants.stream()
                .filter(r -> r.getUuid().equals(uuid))
                .findFirst();
        return response.map(r -> {
            r.update(restaurant);
            return r.toBuilder().build();
        }).orElseThrow();
    }

    public Boolean delete(UUID uuid) {
        return restaurants.removeIf(r -> r.getUuid().equals(uuid));
    }

    public Optional<Restaurant> find(UUID uuid) {
        return restaurants.stream()
                .filter(r -> r.getUuid().equals(uuid))
                .findFirst();
    }

    public List<Restaurant> findAll() {
        return restaurants.stream()
                .map(r -> r.toBuilder().build())
                .collect(Collectors.toList());
    }


}
