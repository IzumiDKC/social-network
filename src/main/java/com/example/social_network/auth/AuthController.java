package com.example.social_network.auth;

import com.example.social_network.auth.dto.AuthDtos;
import com.example.social_network.auth.dto.LoginReq;
import com.example.social_network.auth.dto.RegisterReq;
import com.example.social_network.user.User;
import com.example.social_network.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterReq r) {
        if (repo.findByUsername(r.username()).isPresent()) return ResponseEntity.status(409).body("Username exists");
        if (repo.findByEmail(r.email()).isPresent())      return ResponseEntity.status(409).body("Email exists");

        User u = new User();
        u.setUsername(r.username());
        u.setEmail(r.email());
        u.setPassword(encoder.encode(r.password()));
        repo.save(u);

        return ResponseEntity.status(201).body("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq r) {
        var u = repo.findByUsername(r.username()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if (!encoder.matches(r.password(), u.getPassword())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        String token = jwtUtil.generateToken(u.getUsername());
        return ResponseEntity.ok(new java.util.HashMap<>(){{ put("accessToken", token); put("tokenType","Bearer"); }});
    }

    @GetMapping("/me")
    public Map<String, Object> me(Authentication auth) {
        return Map.of("principal", auth != null ? auth.getName() : null);
    }
}
