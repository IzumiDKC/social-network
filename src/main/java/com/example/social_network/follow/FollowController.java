package com.example.social_network.follow;

import com.example.social_network.follow.dto.UserRef;
import com.example.social_network.security.AuthUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class FollowController {
    private final FollowService service;
    public FollowController(FollowService service){ this.service = service; }

    @PostMapping("/{userId}/follow")
    public void follow(@PathVariable Long userId){
        service.follow(AuthUtils.currentUsername(), userId);
    }

    @DeleteMapping("/{userId}/follow")
    public void unfollow(@PathVariable Long userId){
        service.unfollow(AuthUtils.currentUsername(), userId);
    }

    @GetMapping("/{userId}/followers")
    public Page<UserRef> followers(@PathVariable Long userId,
                                   @RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="10") int size){
        return service.followers(userId, page, size);
    }

    @GetMapping("/{userId}/following")
    public Page<UserRef> following(@PathVariable Long userId,
                                   @RequestParam(defaultValue="0") int page,
                                   @RequestParam(defaultValue="10") int size){
        return service.following(userId, page, size);
    }

    // NEW: trả về set userId mà tôi đang follow
    @GetMapping("/me/following-ids")
    public Set<Long> myFollowingIds() {
        return service.followingIdSet(AuthUtils.currentUsername());
    }
}
