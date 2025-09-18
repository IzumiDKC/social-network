package com.example.social_network.post.dto;

import java.time.OffsetDateTime;

public record PostResp(
        Long id,
        String authorUsername,
        String content,
        String mediaUrl,
        OffsetDateTime createdAt,
        long likeCount,
        long commentCount
) {}