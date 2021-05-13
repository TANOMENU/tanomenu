package tanomenu.repository;

import org.springframework.stereotype.Component;
import tanomenu.models.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductRepository {

    private final List<Product> products = Collections.synchronizedList(new ArrayList<>());

    public Product save(Product product) {
        products.add(product);
        return product.toBuilder().build();
    }

    public Product update(Long id, Product product) {
        var result = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return result.map(p -> {
            p.update(product);
            return p.toBuilder().build();
        }).orElseThrow();
    }

    public Boolean delete(Long id) {
        return products.removeIf(p -> p.getId().equals(id));
    }

    public List<Product> findAll() {
        return products.stream().map(p -> p.toBuilder().build())
                .collect(Collectors.toList());
    }

    public Optional<Product> findId(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }
}