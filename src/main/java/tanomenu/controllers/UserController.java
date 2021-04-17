package tanomenu.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import tanomenu.models.User;

import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> setUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> getUser(@PathVariable UUID uuid) {
        return ResponseEntity.of(userRepository.find(uuid));
    }

    @DeleteMapping("/{uuid}")
    public void deleteUser(@PathVariable UUID uuid) {
        userRepository.delete(uuid);
    }
}