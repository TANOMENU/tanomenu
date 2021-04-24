package tanomenu.repository;

import org.springframework.stereotype.Component;
import tanomenu.models.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserRepository {

    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    public Optional<User> find(UUID uuid) {
        return users.stream()
                .filter(u -> u.getUuid().equals(uuid))
                .findFirst();
    }

    public User save(User user) {
        var u = user.withUuid(UUID.randomUUID());
        users.add(u);
        return u.toBuilder().build();
    }

    public User update(UUID uuid, User user) {
        var result = users.stream()
                .filter(u -> u.getUuid().equals(uuid))
                .findFirst();
        return result.map(u -> {
            u.update(user);
            return u.toBuilder().build();
        }).orElseThrow();
    }

    public boolean delete(UUID uuid) {
        return users.removeIf(u -> u.getUuid().equals(uuid));
    }

    public List<User> findAll() {
        return users.stream()
                .map(u -> u.toBuilder().build())
                .collect(Collectors.toList());
    }

    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

}
