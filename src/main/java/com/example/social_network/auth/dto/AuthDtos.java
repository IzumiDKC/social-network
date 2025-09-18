package com.example.social_network.auth.dto;

public class AuthDtos {
    public record LoginRequest(String username, String password) {}
    public record RegisterRequest(String username, String email, String password) {}
    public record LoginResponse(String accessToken) {}
}

