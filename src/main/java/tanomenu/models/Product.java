package tanomenu.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Product {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Double price;

    public void update(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
    }
}
