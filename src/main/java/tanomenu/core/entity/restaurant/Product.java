package tanomenu.core.entity.restaurant;

import lombok.*;
import tanomenu.core.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product implements Serializable, Entity<Product> {

    private UUID uuid;

    private UUID restaurantUuid;

    private String name;

    private BigDecimal price;

    private CategoryProduct category;

    private String description;

    private UUID image;

    @SneakyThrows
    @Override
    public Product clone() {
        return (Product) super.clone();
    }

    @Override
    public void update(Product product) {
        this.name = product.name;
        this.price = product.price;
        this.category = product.category;
        this.description = product.description;
        this.image = product.image;
    }
}
