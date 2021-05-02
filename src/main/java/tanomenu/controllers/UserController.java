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
    public String setUser(@RequestBody User user) {
        userRepository.save(user);
        return "redirect:/login";
    }

    @PutMapping("/{uuid}")
    public String updateUser(Model model, @Valid @PathVariable UUID uuid, @RequestBody User user) {
        model.addAttribute("user", userRepository.update(uuid, user));
        return "users/{uuid}";
    }

    // TODO Tem necessidade?
    /*@GetMapping
    public List<User> getAllUsers()  {
        return userRepository.findAll();
    }*/

    @GetMapping("/{uuid}")
    public String getUser(@PathVariable UUID uuid, Model model) {
        model.addAttribute("user", userRepository.find(uuid));
        return "/users/{uuid}";
    }

    @DeleteMapping("/{uuid}")
    public String deleteUser(@PathVariable UUID uuid) {
        userRepository.delete(uuid);
        return "redirect:/index";
    }
}