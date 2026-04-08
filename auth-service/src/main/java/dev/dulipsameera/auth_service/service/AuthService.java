package dev.dulipsameera.auth_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.dulipsameera.auth_service.dto.AuthResponse;
import dev.dulipsameera.auth_service.dto.LoginRequest;
import dev.dulipsameera.auth_service.dto.RegisterRequest;
import dev.dulipsameera.auth_service.model.Role;
import dev.dulipsameera.auth_service.model.User;
import dev.dulipsameera.auth_service.repository.RoleRepository;
import dev.dulipsameera.auth_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // 1. Check if username already exists
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Username already taken!");
        }

        // 2. Map String role from DTO to Role Entity from DB
        Role userRole = roleRepository.findByName(request.role().toUpperCase())
            .orElseThrow(() -> new RuntimeException("Role not found"));

        // 3. Create and Save User
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password())); // Hash it!
        user.setRole(userRole);

        User savedUser = userRepository.save(user); // Save to get the ID

        // 4. Generate token
        String token = jwtService.generateToken(savedUser.getUsername());

        // 5. Return the full response DTO
        return new AuthResponse(
            token, 
            savedUser.getRole().getName(), 
            savedUser.getId(), 
            savedUser.getUsername()
        );
    }

    public AuthResponse login(LoginRequest request) {
    // 1. Find user by username
    User user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new RuntimeException("Invalid username or password"));

    // 2. Verify password
    // matches(plainTextFromRequest, hashedEncodedPasswordFromDB)
    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
        throw new RuntimeException("Invalid username or password");
    }

    // 3. Generate Token
    String token = jwtService.generateToken(user.getUsername());

    // 4. Build and return the full response
    return new AuthResponse(
        token,
        user.getRole().getName(),
        user.getId(),
        user.getUsername()
    );
}
}
