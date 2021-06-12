package tanomenu.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import tanomenu.core.entity.restaurant.Address;
import tanomenu.core.entity.restaurant.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RestaurantRegisterDto {

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

    @NotNull
    private MultipartFile image;

    @NotNull
    private Category category;

}
