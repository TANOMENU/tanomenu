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
public class Restaurant implements Model<Restaurant> {

    private UUID uuid;

    private UUID userUuid;

    @NotBlank
    private String name;

    @NotNull
    private String cnpj;

    @NotNull
    private String phone;

    @Override
    public void update(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.cnpj = restaurant.getCnpj();
    }

    @SneakyThrows
    @Override
    public Restaurant clone() {
        return (Restaurant) super.clone();
    }
}
