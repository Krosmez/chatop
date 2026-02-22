package com.chatop.controller;

import com.chatop.dto.request.LoginRequest;
import com.chatop.dto.request.RegisterRequest;
import com.chatop.dto.response.AuthResponse;
import com.chatop.dto.response.ErrorResponse;
import com.chatop.dto.response.UserResponse;
import com.chatop.entity.User;
import com.chatop.service.AuthService;
import com.chatop.utils.Constantes;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification", description = "Gestion de l'authentification et des utilisateurs")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  @Operation(
      summary = "Inscription d'un nouvel utilisateur",
      description = "Crée un nouveau compte utilisateur et retourne un token JWT"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Inscription réussie",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = AuthResponse.class),
              examples = @ExampleObject(
                  name = "Succès",
                  value = Constantes.AUTH_REGISTER_SUCCESS
              )
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Données invalides ou nom d'utilisateur déjà utilisé",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(
                  name = "Email déjà utilisé",
                  value = Constantes.AUTH_REGISTER_ERROR_400
              )
          )
      )
  })
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/login")
  @Operation(
      summary = "Connexion d'un utilisateur",
      description = "Authentifie un utilisateur existant et retourne un token JWT"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Connexion réussie",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = AuthResponse.class),
              examples = @ExampleObject(
                  name = "Succès",
                  value = Constantes.AUTH_LOGIN_SUCCESS
              )
          )
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Email ou mot de passe incorrect",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(
                  name = "Identifiants incorrects",
                  value = Constantes.AUTH_LOGIN_ERROR_401
              )
          )
      )
  })
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(new AuthResponse(authService.login(request)));
  }

  @GetMapping("/me")
  @Operation(
      summary = "Récupération des informations de l'utilisateur connecté",
      description = "Retourne les informations du profil de l'utilisateur actuellement authentifié"
  )
  @SecurityRequirement(name = "bearerAuth")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Informations récupérées avec succès",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UserResponse.class),
              examples = @ExampleObject(
                  name = "Succès",
                  value = Constantes.AUTH_ME_SUCCESS
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
  public ResponseEntity<UserResponse> getCurrentUser() {
    User user = authService.getCurrentUser();
    UserResponse userResponse = new UserResponse(
        user.getName(),
        user.getEmail(),
        user.getCreatedAt(),
        user.getUpdatedAt()
    );
    return ResponseEntity.ok(userResponse);
  }
}