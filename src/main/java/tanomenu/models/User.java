package tanomenu.models;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

    @With
    private UUID uuid;

    @NotBlank(message = "Não pode estar em branco")
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String cpf;

    @NotBlank
    private String password;

    // TODO aqui poderia ser uma lista de restaurantes
    private Restaurant restaurant;

    public void update(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.cpf = user.getCpf();
    }
}