package com.chatop.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Requête de connexion")
public class LoginRequest {

  @Schema(description = "Adresse email de l'utilisateur", example = "user@example.com", required = true)
  private String email;

  @Schema(description = "Mot de passe", example = "password123", required = true)
  private String password;
}