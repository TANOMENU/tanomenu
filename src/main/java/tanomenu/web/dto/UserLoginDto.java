package tanomenu.web.dto;

import javax.validation.constraints.NotBlank;

public class UserLoginDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
