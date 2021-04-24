package tanomenu.models;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Restaurant {

    @With
    private UUID uuid;

    @NotNull
    private User user;

    @NotBlank
    private String nameRestaurant;

    @NotNull
    private String cnpj;

    private List<Product> products;

    public void update(Restaurant restaurant) {
        this.nameRestaurant = restaurant.getNameRestaurant();
        this.cnpj = restaurant.getCnpj();
    }
}