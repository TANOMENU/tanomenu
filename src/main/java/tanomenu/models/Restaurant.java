package tanomenu.models;

import lombok.*;
import tanomenu.models.restaurant.Product;
import tanomenu.models.restaurant.Address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Restaurant implements Model<Restaurant> {

    private UUID uuid;

    private UUID userUuid;

    @NotBlank
    private String companyName;

    @NotBlank
    private String tradeName;

    @NotNull
    private String cnpj;

    @NotNull
    private String phone;

    @NotNull
    private Address address;

    private Map<String, List<Product>> menu;

    @Override
    public void update(Restaurant restaurant) {
        this.companyName = restaurant.getCompanyName();
        this.tradeName = restaurant.getTradeName();
        this.cnpj = restaurant.getCnpj();
        this.address = restaurant.getAddress();
    }

    @SneakyThrows
    @Override
    public Restaurant clone() {
        return (Restaurant) super.clone();
    }
}
