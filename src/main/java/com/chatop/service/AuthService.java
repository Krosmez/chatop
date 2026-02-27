package com.chatop.service;

import com.chatop.dto.request.LoginRequest;
import com.chatop.dto.request.RegisterRequest;
import com.chatop.dto.response.AuthResponse;
import com.chatop.dto.response.UserResponse;
import com.chatop.entity.User;
import com.chatop.exception.BadRequestException;
import com.chatop.exception.ResourceNotFoundException;
import com.chatop.exception.UnauthorizedException;
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
      throw new BadRequestException("Le nom d'utilisateur est déjà utilisé");
    }
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.save(user);
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    return new AuthResponse(jwtTokenProvider.generateToken(authentication));
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
      throw new UnauthorizedException("Utilisateur non authentifié");
    }
    String email = authentication.getName();
    return userRepository.findByEmail(email)
                         .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
  }

  public UserResponse getUserById(Long id) {
    return userRepository.findById(id)
                         .map(user -> new UserResponse(
                             user.getName(),
                             user.getEmail(),
                             user.getCreatedAt(),
                             user.getUpdatedAt()
                         ))
                         .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
  }
}