package com.example.social_network.post.dto;

import jakarta.validation.constraints.*;

public record CreatePostReq(
        @NotBlank String content,
        String mediaUrl
) {}