package tanomenu.core.repository;

import org.springframework.stereotype.Service;
import tanomenu.core.Repository;
import tanomenu.core.entity.restaurant.Product;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductRepository extends Repository<Product> {

    public List<Product> findByRestaurant(UUID uuid) {
        return getStream()
                .filter(p -> p.getRestaurantUuid().equals(uuid))
                .collect(Collectors.toList());
    }

}
