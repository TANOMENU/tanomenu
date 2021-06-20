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
        return getStream()
                .filter(r -> r.getCnpj().equals(cnpj))
                .findFirst();
    }

    public List<Restaurant> findByOwner(UUID uuid) {
        return getStream()
                .filter(r -> r.getUserUuid().equals(uuid))
                .collect(Collectors.toList());
    }

    public List<Restaurant> findByName(String name){
        return getStream()
                .filter(r -> r.getCompanyName().equals(name))
                .collect(Collectors.toList());
    }
}
