package com.chatop.controller;

import com.chatop.dto.request.RentalRequest;
import com.chatop.dto.response.ErrorResponse;
import com.chatop.entity.Rental;
import com.chatop.exception.BadRequestException;
import com.chatop.exception.ImageUploadException;
import com.chatop.service.AuthService;
import com.chatop.service.RentalService;
import com.chatop.utils.Constantes;
import com.chatop.utils.ImageUtils;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Tag(name = "Locations", description = "Gestion des annonces de locations immobilières")
@SecurityRequirement(name = "bearerAuth")
public class RentalController {
  private final RentalService rentalService;
  private final AuthService authService;

  @GetMapping
  @Operation(
      summary = "Liste de toutes les locations",
      description = "Récupère la liste complète de toutes les annonces de locations disponibles"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Liste récupérée avec succès",
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(
                  name = "Succès",
                  value = Constantes.RENTAL_LIST_SUCCESS
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
  public ResponseEntity<Map<String, List<Rental>>> getAllRentals() {
    List<Rental> rentals = rentalService.getAllRentals();
    return ResponseEntity.ok(Map.of("rentals", rentals));
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Récupération d'une location par son ID",
      description = "Retourne les détails complets d'une location spécifique"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Location trouvée",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Rental.class),
              examples = @ExampleObject(
                  name = "Succès",
                  value = Constantes.RENTAL_GET_BY_ID_SUCCESS
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
  public ResponseEntity<Rental> getRentalById(
      @Parameter(description = "ID de la location", required = true)
      @PathVariable Long id
  ) {
    Rental rental = rentalService.getRentalById(id);
    return ResponseEntity.ok(rental);
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(
      summary = "Création d'une nouvelle location",
      description = "Crée une nouvelle annonce de location avec upload d'image"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Location créée avec succès",
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(
                  name = "Succès",
                  value = Constantes.RENTAL_CREATED_SUCCESS
              )
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Données invalides ou image manquante",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(
                  name = "Image manquante",
                  value = Constantes.RENTAL_IMAGE_REQUIRED_ERROR_400
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
          responseCode = "500",
          description = "Erreur lors de l'upload de l'image",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(
                  name = "Erreur upload",
                  value = Constantes.RENTAL_UPLOAD_ERROR_500
              )
          )
      )
  })
  public ResponseEntity<?> createRental(
      @Parameter(description = "Nom de la location", required = true)
      @RequestParam("name") String name,
      @Parameter(description = "Surface en m²", required = true)
      @RequestParam("surface") Double surface,
      @Parameter(description = "Prix par nuit", required = true)
      @RequestParam("price") Double price,
      @Parameter(description = "Description détaillée", required = true)
      @RequestParam("description") String description,
      @Parameter(description = "Image de la location (JPG, PNG)", required = true)
      @RequestParam("picture") MultipartFile picture
  ) {
    if (picture == null || picture.isEmpty()) {
      throw new BadRequestException("L'image est requise");
    }
    try {
      Long ownerId = authService.getCurrentUser().getId();
      String imageUrl = ImageUtils.handleImageUpload(picture);
      RentalRequest request = new RentalRequest(name, surface, price, imageUrl, description, ownerId);
      rentalService.createRental(request);
      return ResponseEntity.ok(Map.of("message", "Rental created !"));
    } catch (IOException e) {
      throw new ImageUploadException("Erreur lors de l'upload de l'image", e);
    }
  }

  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(
      summary = "Mise à jour d'une location",
      description = "Met à jour les informations d'une location existante. L'image est optionnelle."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Location mise à jour avec succès",
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(
                  name = "Succès",
                  value = Constantes.RENTAL_UPDATED_SUCCESS
              )
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Données invalides",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(
                  name = "Données invalides",
                  value = Constantes.RENTAL_INVALID_DATA_ERROR_400
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
  public ResponseEntity<?> updateRental(
      @Parameter(description = "ID de la location", required = true)
      @PathVariable Long id,
      @Parameter(description = "Nom de la location", required = true)
      @RequestParam("name") String name,
      @Parameter(description = "Surface en m²", required = true)
      @RequestParam("surface") Double surface,
      @Parameter(description = "Prix par nuit", required = true)
      @RequestParam("price") Double price,
      @Parameter(description = "Description détaillée", required = true)
      @RequestParam("description") String description,
      @Parameter(description = "Nouvelle image (optionnel)")
      @RequestParam(value = "picture", required = false) MultipartFile picture
  ) {
    try {
      Long ownerId = authService.getCurrentUser().getId();
      Rental existingRental = rentalService.getRentalById(id);
      String imageUrl = existingRental.getPicture();
      if (picture != null && !picture.isEmpty()) {
        imageUrl = ImageUtils.handleImageUpload(picture);
      }
      RentalRequest request = new RentalRequest(name, surface, price, imageUrl, description, ownerId);
      rentalService.updateRental(id, request);
      return ResponseEntity.ok(Map.of("message", "Rental updated !"));
    } catch (IOException e) {
      throw new ImageUploadException("Erreur lors de l'upload de l'image", e);
    }
  }

  @DeleteMapping("/{id}")
  @Operation(
      summary = "Suppression d'une location",
      description = "Supprime définitivement une location de la base de données"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Location supprimée avec succès",
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(
                  name = "Succès",
                  value = Constantes.RENTAL_DELETED_SUCCESS
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
  public ResponseEntity<?> deleteRental(
      @Parameter(description = "ID de la location", required = true)
      @PathVariable Long id
  ) {
    rentalService.deleteRental(id);
    return ResponseEntity.ok(Map.of("message", "Rental deleted !"));
  }
}