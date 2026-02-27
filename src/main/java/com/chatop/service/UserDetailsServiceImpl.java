package com.chatop.service;

import com.chatop.entity.User;
import com.chatop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
                              .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + email));
    return org.springframework.security.core.userdetails.User.builder()
                                                             .username(user.getEmail())
                                                             .password(user.getPassword())
                                                             .roles("USER") // Rôle par défaut, à adapter si rôles
                                                             // spécifiques
                                                             .build();
  }
}
