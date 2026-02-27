package com.chatop.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Requête d'envoi de message")
public class MessageRequest {

  @Schema(description = "ID de l'utilisateur (automatique, fourni par le JWT)", example = "1", accessMode =
      Schema.AccessMode.READ_ONLY)
  private Long user_id;

  @Schema(description = "ID de la location concernée", example = "1", required = true)
  private Long rental_id;

  @Schema(description = "Contenu du message", example = "Je suis intéressé par cette location", required = true)
  private String message;
}
