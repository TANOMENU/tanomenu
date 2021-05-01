package tanomenu.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tanomenu.models.User;
import tanomenu.repository.UserRepository;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public Optional<User> setUser(@RequestBody User user) {
        return userRepository.find(user.getUuid());
    }

    @PutMapping("/{uuid}")
    public User updateUser(Model model, @Valid @PathVariable UUID uuid, @RequestBody User user) {
        return userRepository.update(uuid, user);
    }

    @GetMapping
    public List<User> getAllUsers()  {
        return userRepository.findAll();
    }

    @GetMapping("/{uuid}")
    public Optional<User> getUser(@PathVariable UUID uuid) {
        return userRepository.find(uuid);
    }

    @DeleteMapping("/{uuid}")
    public void deleteUser(@PathVariable UUID uuid) {
        userRepository.delete(uuid);
    }
}