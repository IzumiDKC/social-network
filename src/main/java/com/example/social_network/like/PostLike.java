package com.example.social_network.like;

import com.example.social_network.post.Post;
import com.example.social_network.user.User;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name="post_likes")
@IdClass(PostLike.PK.class)
public class PostLike {
    @Id
    @ManyToOne(optional=false)
    @JoinColumn(name="post_id")
    private Post post;

    @Id
    @ManyToOne(optional=false)
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable=false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    // ---- Getter & Setter ----
    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    // ---- PK class ----
    public static class PK implements Serializable {
        private Long post;
        private Long user;

        public PK() {}
        public PK(Long post, Long user) {
            this.post = post;
            this.user = user;
        }

        @Override public boolean equals(Object o){
            if(this==o) return true;
            if(!(o instanceof PK pk)) return false;
            return Objects.equals(post, pk.post) && Objects.equals(user, pk.user);
        }

        @Override public int hashCode(){ return Objects.hash(post, user); }
    }
}
