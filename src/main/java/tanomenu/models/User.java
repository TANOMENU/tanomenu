package tanomenu.models;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User implements Model<User> {

    private UUID uuid;

    @NotBlank(message = "Não pode estar em branco")
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String cpf;

    @NotBlank
    private String password;

    @SneakyThrows
    @Override
    public User clone() {
        return (User) super.clone();
    }

    public void update(User user) {
        user = (User) user;
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.cpf = user.getCpf();
    }
}