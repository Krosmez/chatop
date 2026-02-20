package com.chatop.service;

import com.chatop.dto.AuthResponse;
import com.chatop.dto.LoginRequest;
import com.chatop.dto.RegisterRequest;
import com.chatop.entity.User;
import com.chatop.repository.UserRepository;
import com.chatop.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;

  public AuthResponse register(RegisterRequest request) {
    if (userRepository.existsByName(request.getName())) {
      throw new RuntimeException("Username is already taken");
    }

    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.save(user);
    // Authentifie l'utilisateur et génère un token
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    String token = jwtTokenProvider.generateToken(authentication);
    System.out.println("User registered and authenticated: " + request.getEmail() + ", || JWT: " + token);
    return new AuthResponse(token); // Crée une classe AuthResponse
  }

  public String login(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return jwtTokenProvider.generateToken(authentication);
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new RuntimeException("Utilisateur non authentifié");
    }

    String email = authentication.getName();
    return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
  }
}