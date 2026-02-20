package com.chatop.controller;

import com.chatop.dto.AuthResponse;
import com.chatop.dto.LoginRequest;
import com.chatop.dto.RegisterRequest;
import com.chatop.dto.UserResponse;
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
    System.out.println("Received registration request: " + request.getEmail() + ", " + request.getName());
    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @GetMapping("/me")
  public ResponseEntity<UserResponse> getCurrentUser() {
    User user = authService.getCurrentUser();
    UserResponse userResponse = new UserResponse(user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    return ResponseEntity.ok(userResponse);
  }
}