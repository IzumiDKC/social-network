package com.example.social_network.comment;

import com.example.social_network.comment.dto.CreateCommentReq;
import com.example.social_network.post.Post;
import com.example.social_network.post.PostRepository;
import com.example.social_network.user.User;
import com.example.social_network.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    private final CommentRepository repo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public CommentService(CommentRepository repo, PostRepository postRepo, UserRepository userRepo) {
        this.repo = repo; this.postRepo = postRepo; this.userRepo = userRepo;
    }

    @Transactional
    public Comment add(Long postId, String username, CreateCommentReq req){
        Post post = postRepo.findById(postId).orElseThrow(EntityNotFoundException::new);
        User author = userRepo.findByUsername(username).orElseThrow();
        Comment c = new Comment();
        c.setPost(post); c.setAuthor(author); c.setContent(req.content());
        return repo.save(c);
    }

    public Page<Comment> list(Long postId, int page, int size){
        return repo.findByPost_IdOrderByCreatedAtDesc(postId, PageRequest.of(page, size));
    }
}
