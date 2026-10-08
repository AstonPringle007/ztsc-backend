package com.ztsc.backend.auth.service;

import com.ztsc.backend.auth.dto.AuthResponse;
import com.ztsc.backend.auth.dto.LoginRequest;
import com.ztsc.backend.auth.dto.RegisterRequest;
import com.ztsc.backend.auth.entity.Role;
import com.ztsc.backend.auth.entity.User;
import com.ztsc.backend.auth.repository.UserRepository;
import com.ztsc.backend.security.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {

        // 1. Check username
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // 2. Check email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // 3. Create User
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // 4. Hash password
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // 5. Default role
        user.setRole(Role.PROCUREMENT);

        // 6. Enable account
        user.setEnabled(true);

        // 7. Save to database
        userRepository.save(user);

        // 8. Return response
        return new AuthResponse(
                "Registration successful",
                user.getUsername(),
                user.getRole().name(),
                null
        );
    }

    public AuthResponse login(LoginRequest request) {

        // 1. Find user
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password")
                );

        // 2. Verify password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("Invalid username or password");
        }

        // 3. Check account status
        if (!user.isEnabled()) {
            throw new RuntimeException("User account is disabled");
        }

        // 4. Generate JWT
        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        // 5. Return response
        return new AuthResponse(
                "Login successful",
                user.getUsername(),
                user.getRole().name(),
                token
        );
    }
}