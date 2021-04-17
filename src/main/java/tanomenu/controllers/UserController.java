package tanomenu.controllers;

import tanomenu.models.User;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private List<User> users = new ArrayList<>();

    @PostMapping("/")
    public HttpStatus setUser(@RequestBody User user) {
        Long lastId = (long) users.toArray().length + 1;
        user.setId(lastId);
        if(!user.notComplete()) {
            users.add(user);
            return HttpStatus.CREATED;
        }

        return HttpStatus.BAD_REQUEST;
    }

    @GetMapping("/")
    public List<User> getAll() {
        if(!users.isEmpty()) {
            return users;
        }

        return null;
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable("id") Long id) {
        Optional<User> userFind = users.stream().filter(user -> user.getId().equals(id)).findFirst();

        if (userFind.isPresent()) {
            return userFind.get();
        }

        return null;
    }
}