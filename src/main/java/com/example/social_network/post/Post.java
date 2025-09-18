package com.example.social_network.post;

import com.example.social_network.user.User;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    private String mediaUrl;

    @Column(nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    // ===== GETTERS/SETTERS =====
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getAuthor() { return author; }

    public void setAuthor(User author) { this.author = author; }   // <— cần cho PostService

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getMediaUrl() { return mediaUrl; }

    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }

    public OffsetDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
