package dev.dulipsameera.auth_service.dto;

public record AuthResponse(
    String token,
    String role,
    Long id,
    String username
) {}
