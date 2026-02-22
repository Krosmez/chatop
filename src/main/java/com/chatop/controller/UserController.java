package com.chatop.controller;

import com.chatop.dto.response.ErrorResponse;
import com.chatop.dto.response.UserResponse;
import com.chatop.service.AuthService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs", description = "Gestion des informations utilisateur")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
  private final AuthService authService;

  @GetMapping("/{id}")
  @Operation(
      summary = "Récupération des informations d'un utilisateur",
      description = "Retourne les informations publiques d'un utilisateur par son ID"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Utilisateur trouvé",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UserResponse.class),
              examples = @ExampleObject(
                  name = "Succès",
                  value = Constantes.USER_GET_BY_ID_SUCCESS
              )
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Utilisateur non trouvé",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(
                  name = "Utilisateur introuvable",
                  value = Constantes.USER_NOT_FOUND_ERROR_404
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
      )
  })
  public ResponseEntity<UserResponse> getUserById(
      @Parameter(description = "ID de l'utilisateur", required = true)
      @PathVariable Long id
  ) {
    UserResponse userResponse = authService.getUserById(id);
    return ResponseEntity.ok(userResponse);
  }
}
