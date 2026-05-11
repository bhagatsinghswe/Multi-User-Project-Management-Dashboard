package com.assessment.taskmanager.controller;

import com.assessment.taskmanager.dto.AuthRequest;
import com.assessment.taskmanager.dto.AuthResponse;
import com.assessment.taskmanager.dto.RegisterRequest;
import com.assessment.taskmanager.dto.UserProfile;
import com.assessment.taskmanager.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfile> me(@AuthenticationPrincipal com.assessment.taskmanager.model.entity.User user) {
        return ResponseEntity.ok(new UserProfile(user.getUsername(), user.getRole().name()));
    }
}