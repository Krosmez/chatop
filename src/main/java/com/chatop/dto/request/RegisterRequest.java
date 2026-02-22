package com.chatop.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Requête d'inscription")
public class RegisterRequest {

  @Schema(description = "Nom d'utilisateur", example = "John Doe", required = true)
  private String name;

  @Schema(description = "Adresse email", example = "john.doe@example.com", required = true)
  private String email;

  @Schema(description = "Mot de passe", example = "securePassword123", required = true)
  private String password;
}