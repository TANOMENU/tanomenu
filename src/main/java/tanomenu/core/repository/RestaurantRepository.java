package tanomenu.core.repository;

import org.springframework.stereotype.Service;
import tanomenu.core.Repository;
import tanomenu.core.entity.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RestaurantRepository extends Repository<Restaurant> {

    public Optional<Restaurant> findByCnpj(String cnpj) {
        return data.stream()
                .parallel()
                .map(Restaurant::clone)
                .filter(r -> r.getCnpj().equals(cnpj))
                .findFirst();
    }

    public List<Restaurant> findByOwner(UUID uuid) {
        return data.stream()
                .parallel()
                .map(Restaurant::clone)
                .filter(r -> r.getUserUuid().equals(uuid))
                .collect(Collectors.toList());
    }

}
