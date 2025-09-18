package com.example.social_network.user;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repo;

    // Constructor injection
    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<User> all() {
        return repo.findAll();
    }
}
