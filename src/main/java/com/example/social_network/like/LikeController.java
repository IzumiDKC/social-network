package com.example.social_network.like;

import com.example.social_network.security.AuthUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}")
public class LikeController {
    private final LikeService service;
    public LikeController(LikeService service){ this.service = service; }

    @PostMapping("/like")
    public void like(@PathVariable Long postId){
        service.like(postId, AuthUtils.currentUsername());
    }

    @DeleteMapping("/like")
    public void unlike(@PathVariable Long postId){
        service.unlike(postId, AuthUtils.currentUsername());
    }
}