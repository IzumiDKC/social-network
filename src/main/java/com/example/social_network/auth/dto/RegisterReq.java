package com.example.social_network.auth.dto;

import jakarta.validation.constraints.*;

public record RegisterReq(
        @NotBlank @Size(max=30) String username,
        @Email String email,
        @NotBlank @Size(min=6,max=100) String password
) {}
