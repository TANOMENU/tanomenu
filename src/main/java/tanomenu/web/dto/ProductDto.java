package tanomenu.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import tanomenu.core.entity.restaurant.CategoryProduct;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductDto {

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
