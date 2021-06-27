package tanomenu.core.repository;

import org.springframework.stereotype.Service;
import tanomenu.core.Repository;
import tanomenu.core.entity.Restaurant;
import tanomenu.core.entity.restaurant.Product;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
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
        Predicate<Restaurant> isCompanyName = r -> containsIgnoreCase(r.getCompanyName(), name);
        Predicate<Restaurant> isTradeName = r -> containsIgnoreCase(r.getTradeName(), name);
        Predicate<Restaurant> isCategory = r -> containsIgnoreCase(r.getCategory().getValue(), name);
        return getStream()
                .filter(isCompanyName.or(isTradeName).or(isCategory))
                .collect(Collectors.toList());
    }



    private boolean containsIgnoreCase(String target, String term) {
        return  target.toLowerCase().contains(term.toLowerCase());
    }
}
