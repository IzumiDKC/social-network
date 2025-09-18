package com.example.social_network.comment.dto;

import jakarta.validation.constraints.*;

public record CreateCommentReq(@NotBlank String content) {

}
