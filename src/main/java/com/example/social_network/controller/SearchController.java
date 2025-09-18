package com.example.social_network.controller;

import com.example.social_network.post.Post;
import com.example.social_network.post.PostRepository;
import com.example.social_network.user.User;
import com.example.social_network.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final UserRepository userRepo;
    private final PostRepository postRepo;

    public SearchController(UserRepository userRepo, PostRepository postRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @GetMapping
    public Map<String, Object> search(@RequestParam String q) {
        List<User> users = userRepo.findByUsernameContainingIgnoreCase(q);
        List<Post> posts = postRepo.findByContentContainingIgnoreCase(q);

        Map<String, Object> result = new HashMap<>();
        result.put("users", users);
        result.put("posts", posts);
        return result;
    }
}


