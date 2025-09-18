package com.example.social_network.user;

import com.example.social_network.security.AuthUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me")
public class UserMeController {
    private final UserRepository repo;
    public UserMeController(UserRepository repo){ this.repo = repo; }

    @GetMapping
    public User me(){
        String username = AuthUtils.currentUsername();
        return repo.findByUsername(username).orElseThrow();
    }
}
