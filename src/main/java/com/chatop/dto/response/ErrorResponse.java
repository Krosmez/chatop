package com.chatop.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Réponse d'erreur standard de l'API")
public class ErrorResponse {

  @Schema(description = "Message d'erreur détaillé", example = "Resource not found")
  private String error;

  @Schema(description = "Code de statut HTTP", example = "404")
  private int status;

  @Schema(description = "Timestamp de l'erreur", example = "2026-02-22T10:30:00")
  private LocalDateTime timestamp;

  public ErrorResponse(String error, int status) {
    this.error = error;
    this.status = status;
    this.timestamp = LocalDateTime.now();
  }
}

