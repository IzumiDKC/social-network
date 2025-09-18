package com.example.social_network.post;

import com.example.social_network.post.dto.CreatePostReq;
import com.example.social_network.post.dto.PostResp;
import com.example.social_network.security.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@SecurityRequirement(name = "bearerAuth")
public class PostController {
    private final PostService service;
    public PostController(PostService service){ this.service = service; }

    @PostMapping
    public PostResp create(@RequestBody @Valid CreatePostReq req){
        return service.create(AuthUtils.currentUsername(), req);
    }

    @Operation(summary = "List posts (newest first)")
    @GetMapping
    public Page<PostResp> list(@RequestParam(defaultValue="0") int page,
                               @RequestParam(defaultValue="10") int size){
        return service.listAll(page, size);
    }

    @GetMapping("/user/{username}")
    public Page<PostResp> listOfUser(@PathVariable String username,
                                     @RequestParam(defaultValue="0") int page,
                                     @RequestParam(defaultValue="10") int size){
        return service.listOfUser(username, page, size);
    }

    @GetMapping("/{id}")
    public PostResp get(@PathVariable Long id){ return service.get(id); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id, AuthUtils.currentUsername());
    }

    @GetMapping("/feed")
    public Page<PostResp> feed(@RequestParam(defaultValue="0") int page,
                               @RequestParam(defaultValue="10") int size){
        return service.feed(AuthUtils.currentUsername(), page, size);
    }
}
