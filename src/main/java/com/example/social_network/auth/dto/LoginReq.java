package com.example.social_network.auth.dto;

import jakarta.validation.constraints.*;

public record LoginReq(
        @NotBlank String username,
        @NotBlank String password
) {}