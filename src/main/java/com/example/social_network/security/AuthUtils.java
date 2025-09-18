package com.example.social_network.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthUtils {
    private AuthUtils() {}
    public static String currentUsername() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return (a != null) ? a.getName() : null;
    }
}
