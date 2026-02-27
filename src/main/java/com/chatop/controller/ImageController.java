package com.chatop.controller;

import com.chatop.dto.response.ErrorResponse;
import com.chatop.utils.Constantes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
@Tag(name = "Images", description = "Récupération des images uploadées")
public class ImageController {

  @GetMapping("/{filename:.+}")
  @Operation(
      summary = "Récupération d'une image",
      description = "Retourne une image uploadée par son nom de fichier. Cette route est publique (pas " +
          "d'authentification requise)."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Image trouvée",
          content = @Content(
              mediaType = "image/jpeg",
              schema = @Schema(type = "string", format = "binary")
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Image non trouvée",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(
                  name = "Image introuvable",
                  value = Constantes.IMAGE_NOT_FOUND_ERROR_404
              )
          )
      )
  })
  public ResponseEntity<Resource> getImage(
      @Parameter(description = "Nom du fichier image", required = true, example = "1771688173705_gal-yosef-01.jpg")
      @PathVariable String filename
  ) throws IOException {
    Path filePath = Paths.get("uploads", filename);
    if (!Files.exists(filePath)) {
      return ResponseEntity.notFound().build();
    }
    Resource fileResource = new UrlResource(filePath.toUri());
    return ResponseEntity.ok()
                         .header("Content-Type", Files.probeContentType(filePath))
                         .body(fileResource);
  }
}