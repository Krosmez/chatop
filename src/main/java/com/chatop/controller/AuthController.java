package com.chatop.controller;

import com.chatop.dto.request.LoginRequest;
import com.chatop.dto.request.RegisterRequest;
import com.chatop.dto.response.AuthResponse;
import com.chatop.dto.response.UserResponse;
import com.chatop.entity.User;
import com.chatop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
    try {
      return ResponseEntity.ok(authService.register(request));

    } catch (RuntimeException e) {
      return ResponseEntity.status(400).body(new AuthResponse(e.getMessage()));
    }
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    try {
      return ResponseEntity.ok(new AuthResponse(authService.login(request)));
    } catch (RuntimeException e) {
      return ResponseEntity.status(401).body(new AuthResponse(e.getMessage()));
    }
  }

  @GetMapping("/me")
  public ResponseEntity<UserResponse> getCurrentUser() {
    try {
      User user = authService.getCurrentUser();
      UserResponse userResponse = new UserResponse(user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
      return ResponseEntity.ok(userResponse);
    } catch (RuntimeException e) {
      return ResponseEntity.status(401).build();
    }
  }
}