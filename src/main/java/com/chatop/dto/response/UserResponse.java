package com.chatop.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Informations publiques d'un utilisateur")
public class UserResponse {

  @Schema(description = "Nom de l'utilisateur", example = "John Doe")
  private String name;

  @Schema(description = "Adresse email", example = "john.doe@example.com")
  private String email;

  @Schema(description = "Date de création du compte", example = "2026-02-21T10:30:00")
  private LocalDateTime created_at;

  @Schema(description = "Date de dernière mise à jour", example = "2026-02-21T10:30:00")
  private LocalDateTime updated_at;
}
