package com.chatop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
  @Value("${app.jwt-secret}")
  private String jwtSecret;
  @Value("${app.jwt-expiration-ms}")
  private long jwtExpirationMs;

  public String generateToken(Authentication authentication) {
    String username = authentication.getName();
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
    System.out.println("Generating JWT for user: " + username);
    try {
      SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
      return Jwts.builder()
              .subject(username)
              .issuedAt(now)
              .expiration(expiryDate)
              .signWith(key)
              .compact();
    } catch (Exception e) {
      throw new RuntimeException("Erreur lors de la génération du token", e);
    }
  }

  public String getUsernameFromJWT(String token) {
    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    Claims claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    System.out.println("Extracted username from JWT: " + claims.getSubject());
    return claims.getSubject();
  }

  public boolean validateToken(String token) {
    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    try {
      Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
      System.out.println("JWT token is valid");
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
}