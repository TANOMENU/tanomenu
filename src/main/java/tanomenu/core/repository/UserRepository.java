package tanomenu.core.repository;

import org.springframework.stereotype.Service;
import tanomenu.core.Repository;
import tanomenu.core.entity.User;

import java.util.*;

@Service
public class UserRepository extends Repository<User> {

    public Optional<User> findByEmail(String email) {
        return data.stream()
                .filter(u -> u.getEmail().equals(email))
                .map(User::clone)
                .findFirst();
    }

}
