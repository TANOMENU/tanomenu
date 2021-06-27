package tanomenu.core.entity;

import lombok.*;
import tanomenu.core.Entity;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User implements Entity<User> {

    private UUID uuid;

    private String name;

    private String email;

    private String cpf;

    private String password;

    private UUID image;

    @SneakyThrows
    @Override
    public User clone() {
        return (User) super.clone();
    }

    public void update(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.cpf = user.getCpf();
        this.image = user.getImage();
    }

}