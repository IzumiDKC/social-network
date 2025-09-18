package com.example.social_network.post;

import com.example.social_network.comment.CommentRepository;
import com.example.social_network.like.PostLikeRepository;
import com.example.social_network.post.dto.CreatePostReq;
import com.example.social_network.post.dto.PostResp;
import com.example.social_network.user.User;
import com.example.social_network.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final PostLikeRepository likeRepo;
    private final CommentRepository cmtRepo;

    public PostService(PostRepository postRepo, UserRepository userRepo,
                       PostLikeRepository likeRepo, CommentRepository cmtRepo) {
        this.postRepo = postRepo; this.userRepo = userRepo;
        this.likeRepo = likeRepo; this.cmtRepo = cmtRepo;
    }

    @Transactional
    public PostResp create(String username, CreatePostReq req){
        User author = userRepo.findByUsername(username).orElseThrow();
        Post p = new Post();
        p.setAuthor(author);
        p.setContent(req.content());
        p.setMediaUrl(req.mediaUrl());
        p = postRepo.save(p);
        return map(p);
    }

    public Page<PostResp> listAll(int page, int size){
        Page<Post> posts = postRepo.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
        return posts.map(this::map);
    }

    public Page<PostResp> listOfUser(String username, int page, int size){
        Page<Post> posts = postRepo.findAllByAuthor_UsernameOrderByCreatedAtDesc(username, PageRequest.of(page, size));
        return posts.map(this::map);
    }

    public PostResp get(Long id){
        Post p = postRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        return map(p);
    }

    @Transactional
    public void delete(Long id, String actorUsername){
        Post p = postRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        if(!p.getAuthor().getUsername().equals(actorUsername))
            throw new IllegalStateException("Not owner");
        postRepo.delete(p);
    }

    private PostResp map(Post p){
        long likes = likeRepo.countByPost_Id(p.getId());
        long cmts  = cmtRepo.countByPost_Id(p.getId());
        return new PostResp(
                p.getId(), p.getAuthor().getUsername(), p.getContent(), p.getMediaUrl(), p.getCreatedAt(),
                likes, cmts
        );
    }
    public Page<PostResp> feed(String actorUsername, int page, int size){
        var user = userRepo.findByUsername(actorUsername).orElseThrow();
        return postRepo.feedOf(user.getId(), PageRequest.of(page, size))
                .map(this::map);
    }
}