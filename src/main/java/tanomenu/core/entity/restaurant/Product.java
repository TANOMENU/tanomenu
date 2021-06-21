package tanomenu.core.entity.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product implements Serializable {

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private CategoryProduct category;

    @NotBlank
    private String description;

    private String image;


}
