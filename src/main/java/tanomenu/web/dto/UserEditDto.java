package tanomenu.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import tanomenu.core.entity.User;

import javax.validation.constraints.NotBlank;

@Data
public class UserEditDto {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String cpf;

    private MultipartFile image;

    private String password;

    @NotBlank
    private String confirmPassword;

}
