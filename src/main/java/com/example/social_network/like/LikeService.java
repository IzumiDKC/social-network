package com.example.social_network.like;

import com.example.social_network.post.Post;
import com.example.social_network.post.PostRepository;
import com.example.social_network.user.User;
import com.example.social_network.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {
    private final PostLikeRepository repo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public LikeService(PostLikeRepository repo, PostRepository postRepo, UserRepository userRepo) {
        this.repo = repo; this.postRepo = postRepo; this.userRepo = userRepo;
    }

    @Transactional
    public void like(Long postId, String username){
        Post post = postRepo.findById(postId).orElseThrow(EntityNotFoundException::new);
        User user = userRepo.findByUsername(username).orElseThrow();
        if (!repo.existsByPost_IdAndUser_Id(postId, user.getId())) {
            PostLike pl = new PostLike();
            pl.setPost(post); pl.setUser(user);
            repo.save(pl);
        }
    }

    @Transactional
    public void unlike(Long postId, String username){
        User user = userRepo.findByUsername(username).orElseThrow();
        repo.deleteByPost_IdAndUser_Id(postId, user.getId());
    }
}