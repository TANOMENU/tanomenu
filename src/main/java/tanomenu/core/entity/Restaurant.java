package tanomenu.core.entity;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import tanomenu.core.Entity;
import tanomenu.core.entity.restaurant.Category;
import tanomenu.core.entity.restaurant.Product;
import tanomenu.core.entity.restaurant.Address;

import java.util.List;
import java.util.Map;
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
