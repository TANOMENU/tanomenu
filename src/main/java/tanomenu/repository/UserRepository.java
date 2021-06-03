package tanomenu.repository;

import org.springframework.stereotype.Component;
import tanomenu.models.User;

import java.util.*;

@Component
public class UserRepository extends Repository<User> {

    public Optional<User> findByEmail(String email) {
        return data.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

}
