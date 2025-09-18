package com.example.social_network.follow;

import com.example.social_network.user.User;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name="follows")
public class Follow {

    @EmbeddedId
    private FollowId id;

    @ManyToOne @MapsId("followerId")
    @JoinColumn(name="follower_id")
    private User follower;

    @ManyToOne @MapsId("followeeId")
    @JoinColumn(name="followee_id")
    private User followee;

    @Column(nullable=false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    // --- thêm getter/setter thủ công ---
    public FollowId getId() { return id; }
    public void setId(FollowId id) { this.id = id; }

    public User getFollower() { return follower; }
    public void setFollower(User follower) { this.follower = follower; }

    public User getFollowee() { return followee; }
    public void setFollowee(User followee) { this.followee = followee; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
