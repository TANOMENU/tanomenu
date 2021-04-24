package tanomenu.repository;

import org.springframework.stereotype.Component;
import tanomenu.models.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserRepository {

    private RestaurantRepository restaurantRepository;
    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    public Optional<User> find(UUID uuid) {
        return users.stream()
                .filter(u -> u.getUuid().equals(uuid))
                .findFirst();
    }


    // TODO verificar com o grupo se o save do user poderá já incluir um restaurante
    public User save(User user) {
        var u = user.withUuid(UUID.randomUUID());
        users.add(u);
        return u.toBuilder().build();
    }

    // TODO verificar com o grupo melhores maneira de implementar o update
    public User update(UUID uuid, User user) {
        var result = users.stream()
                .filter(u -> u.getUuid().equals(uuid))
                .findFirst();
        System.out.println(uuid+ " " +user);
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
