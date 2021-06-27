package tanomenu.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import tanomenu.core.entity.restaurant.CategoryProduct;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductDto {

    @NotBlank
    private UUID uuid;

    @NotBlank
    private UUID restaurantUuid;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private CategoryProduct category;

    @NotBlank
    private String description;

    private MultipartFile image;
}
