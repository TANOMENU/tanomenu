package tanomenu.core.repository;

import org.springframework.stereotype.Service;
import tanomenu.core.Repository;
import tanomenu.core.entity.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.*;
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
        Predicate<String> contains = target -> target.toLowerCase().contains(name.toLowerCase());
        Predicate<Restaurant> isCompanyName = r -> contains.test(r.getCompanyName());
        Predicate<Restaurant> isTradeName = r -> contains.test(r.getTradeName());
        Predicate<Restaurant> isCategory = r -> contains.test(r.getCategory().getValue());
        return getStream()
                .filter(isCompanyName.or(isTradeName).or(isCategory))
                .collect(Collectors.toList());
    }

    public List<Restaurant> shuffle(int limit) {
        return getStream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                    Collections.shuffle(collected);
                    return collected.stream();
                }))
                .limit(limit)
                .collect(Collectors.toList());
    }

}
