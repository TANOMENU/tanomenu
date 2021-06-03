package tanomenu.models;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Product implements Model<Product> {

    private UUID uuid;

    private UUID restaurantUuid;

    @NotBlank
    private String name;

    @NotNull
    private Double price;

    @Override
    public void update(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
    }

    @SneakyThrows
    @Override
    public Product clone() {
        return (Product) super.clone();
    }
}
