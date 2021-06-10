package tanomenu.repository;

import org.springframework.stereotype.Service;
import tanomenu.models.Restaurant;

import java.util.Optional;

@Service
public class RestaurantRepository extends Repository<Restaurant> {

    public Optional<Restaurant> findByCnpj(String cnpj) {
        return data.stream()
                .filter(r -> r.getCnpj().equals(cnpj))
                .map(Restaurant::clone)
                .findFirst();
    }

}
