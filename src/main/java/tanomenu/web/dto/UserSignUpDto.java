package tanomenu.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserSignUpDto {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String cpf;

    @NotBlank
    private String password;

}
