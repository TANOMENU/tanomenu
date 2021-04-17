package tanomenu.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String localization;
    private String cpf;

    public boolean notComplete(){
        return (name == null)||(email == null)||(password == null);
    }

}