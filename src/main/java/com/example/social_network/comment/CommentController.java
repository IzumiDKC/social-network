package com.example.social_network.comment;

import com.example.social_network.comment.dto.CreateCommentReq;
import com.example.social_network.security.AuthUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    private final CommentService service;
    public CommentController(CommentService service){ this.service = service; }

    @PostMapping
    public Comment add(@PathVariable Long postId, @RequestBody @Valid CreateCommentReq req){
        return service.add(postId, AuthUtils.currentUsername(), req);
    }

    @GetMapping
    public Page<Comment> list(@PathVariable Long postId,
                              @RequestParam(defaultValue="0") int page,
                              @RequestParam(defaultValue="10") int size){
        return service.list(postId, page, size);
    }
}