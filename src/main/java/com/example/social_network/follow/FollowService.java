package com.example.social_network.follow;

import com.example.social_network.follow.dto.UserRef;
import com.example.social_network.user.User;
import com.example.social_network.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {
    private final FollowRepository repo;
    private final UserRepository userRepo;

    public FollowService(FollowRepository repo, UserRepository userRepo) {
        this.repo = repo; this.userRepo = userRepo;
    }


    @Transactional
    public void follow(String actorUsername, Long followeeId){
        User follower = userRepo.findByUsername(actorUsername).orElseThrow();
        // tự foolow bản thân
        if (follower.getId().equals(followeeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot follow yourself");
        }

        User followee = userRepo.findById(followeeId).orElseThrow(EntityNotFoundException::new);

        if (!repo.existsByFollower_IdAndFollowee_Id(follower.getId(), followee.getId())) {
            Follow f = new Follow();
            f.setFollower(follower);
            f.setFollowee(followee);
            f.setId(new FollowId(follower.getId(), followee.getId()));
            repo.save(f);
        }
    }

    @Transactional
    public void unfollow(String actorUsername, Long followeeId){
        User follower = userRepo.findByUsername(actorUsername).orElseThrow();
        //  derived query:
        repo.deleteByFollower_IdAndFollowee_Id(follower.getId(), followeeId);
        // repo.deleteById(new FollowId(follower.getId(), followeeId));
    }


    @Transactional(readOnly = true)
    public Page<UserRef> followers(Long userId, int page, int size){
        return repo.findByFollowee_Id(userId, PageRequest.of(page, size))
                .map(f -> new UserRef(f.getFollower().getId(), f.getFollower().getUsername()));
    }

    @Transactional(readOnly = true)
    public Page<UserRef> following(Long userId, int page, int size){
        return repo.findByFollower_Id(userId, PageRequest.of(page, size))
                .map(f -> new UserRef(f.getFollowee().getId(), f.getFollowee().getUsername()));
    }

    @Transactional(readOnly = true)
    public Set<Long> followingIdSet(String actorUsername){
        User follower = userRepo.findByUsername(actorUsername).orElseThrow();
        List<Long> ids = repo.findFolloweeIdsByFollowerId(follower.getId());
        return new HashSet<>(ids);
    }
}
