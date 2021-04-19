package tanomenu.models;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

    @With
    private UUID uuid;

    @NotNull
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String location;

    @NotBlank
    private String cpf;

    // TODO aqui poderia ser uma lista de restaurantes
    private Restaurant restaurant;

    public boolean notComplete() {
        return (name == null)||(email == null)||(password == null);
    }

    public void update(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.location = user.getLocation();
        this.cpf = user.getCpf();
    }
}