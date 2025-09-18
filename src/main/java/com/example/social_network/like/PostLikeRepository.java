package com.example.social_network.like;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLike.PK> {
    boolean existsByPost_IdAndUser_Id(Long postId, Long userId);
    long countByPost_Id(Long postId);
    void deleteByPost_IdAndUser_Id(Long postId, Long userId);
}
