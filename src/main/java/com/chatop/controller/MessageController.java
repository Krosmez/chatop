package com.chatop.controller;

import com.chatop.dto.request.MessageRequest;
import com.chatop.dto.response.ErrorResponse;
import com.chatop.service.MessageService;
import com.chatop.utils.Constantes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Messages", description = "Gestion de l'envoi de messages")
@SecurityRequirement(name = "bearerAuth")
public class MessageController {
  private final MessageService messageService;

  @PostMapping
  @Operation(
      summary = "Envoi d'un message",
      description = "Permet à un utilisateur d'envoyer un message concernant une location"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Message envoyé avec succès",
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(
                  name = "Succès",
                  value = Constantes.MESSAGE_SENT_SUCCESS
              )
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Données invalides (message vide ou rental_id manquant)",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = {
                  @ExampleObject(
                      name = "Message vide",
                      value = Constantes.MESSAGE_EMPTY_ERROR_400
                  ),
                  @ExampleObject(
                      name = "ID location manquant",
                      value = Constantes.MESSAGE_RENTAL_ID_MISSING_ERROR_400
                  )
              }
          )
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Token JWT manquant ou invalide",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(
                  name = "Non authentifié",
                  value = Constantes.UNAUTHORIZED_ERROR_401
              )
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Location non trouvée",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(
                  name = "Location introuvable",
                  value = Constantes.RENTAL_NOT_FOUND_ERROR_404
              )
          )
      )
  })
  public ResponseEntity<?> sendMessage(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Contenu du message avec l'ID de la location",
          required = true,
          content = @Content(
              schema = @Schema(implementation = MessageRequest.class),
              examples = @ExampleObject(
                  name = "Exemple de message",
                  value = Constantes.MESSAGE_REQUEST_EXAMPLE
              )
          )
      )
      @RequestBody MessageRequest request
  ) {
    messageService.sendMessage(request);
    return ResponseEntity.ok(Map.of("message", "Message send with success"));
  }

  @PostMapping("/rental/{rentalId}")
  @Operation(
      summary = "Envoi d'un message pour une location spécifique",
      description = "Permet à un utilisateur d'envoyer un message en spécifiant l'ID de la location dans l'URL"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Message envoyé avec succès",
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(
                  name = "Succès",
                  value = Constantes.MESSAGE_SENT_SUCCESS
              )
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Message vide",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(
                  name = "Message vide",
                  value = Constantes.MESSAGE_EMPTY_ERROR_400
              )
          )
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Token JWT manquant ou invalide",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(
                  name = "Non authentifié",
                  value = Constantes.UNAUTHORIZED_ERROR_401
              )
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Location non trouvée",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(
                  name = "Location introuvable",
                  value = Constantes.RENTAL_NOT_FOUND_ERROR_404
              )
          )
      )
  })
  public ResponseEntity<?> sendMessageToRental(
      @Parameter(description = "ID de la location", required = true)
      @PathVariable Long rentalId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Contenu du message",
          required = true,
          content = @Content(
              schema = @Schema(implementation = MessageRequest.class),
              examples = @ExampleObject(
                  name = "Exemple de message",
                  value = Constantes.MESSAGE_REQUEST_SIMPLE_EXAMPLE
              )
          )
      )
      @RequestBody MessageRequest request
  ) {
    request.setRental_id(rentalId);
    messageService.sendMessage(request);
    return ResponseEntity.ok(Map.of("message", "Message send with success"));
  }
}
