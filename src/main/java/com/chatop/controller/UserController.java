package com.chatop.controller;

import com.chatop.dto.response.UserResponse;
import com.chatop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
  private final AuthService authService;

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUSerById(@PathVariable Long id) {
    try {
      UserResponse userResponse = authService.getUserById(id);
      if (userResponse == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(userResponse);
    } catch (RuntimeException e) {
      return ResponseEntity.status(401).build();
    }
  }

}
