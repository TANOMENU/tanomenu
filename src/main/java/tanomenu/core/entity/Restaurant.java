package tanomenu.core.entity;

import lombok.*;
import tanomenu.core.Entity;
import tanomenu.core.entity.restaurant.Category;
import tanomenu.core.entity.restaurant.Address;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Restaurant implements Entity<Restaurant> {

    private UUID uuid;

    private UUID userUuid;

    private String companyName;

    private String tradeName;

    private String cnpj;

    private String phone;

    private Address address;

    private UUID image;

    private Category category;

    @Override
    public void update(Restaurant restaurant) {
        this.companyName = restaurant.getCompanyName();
        this.tradeName = restaurant.getTradeName();
        this.cnpj = restaurant.getCnpj();
        this.address = restaurant.getAddress();
        this.image = restaurant.getImage();
    }

    @SneakyThrows
    @Override
    public Restaurant clone() {
        return (Restaurant) super.clone();
    }
}
