package com.chatop.controller;

import com.chatop.dto.request.RentalRequest;
import com.chatop.entity.Rental;
import com.chatop.exception.BadRequestException;
import com.chatop.exception.ImageUploadException;
import com.chatop.service.AuthService;
import com.chatop.service.RentalService;
import com.chatop.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
  private final RentalService rentalService;
  private final AuthService authService;

  @GetMapping
  public ResponseEntity<Map<String, List<Rental>>> getAllRentals() {
    List<Rental> rentals = rentalService.getAllRentals();
    return ResponseEntity.ok(Map.of("rentals", rentals));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
    Rental rental = rentalService.getRentalById(id);
    return ResponseEntity.ok(rental);
  }

  @PostMapping(consumes = "multipart/form-data")
  public ResponseEntity<?> createRental(
          @RequestParam("name") String name,
          @RequestParam("surface") Double surface,
          @RequestParam("price") Double price,
          @RequestParam("description") String description,
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

  @PutMapping(value = "/{id}", consumes = "multipart/form-data")
  public ResponseEntity<?> updateRental(
          @PathVariable Long id,
          @RequestParam("name") String name,
          @RequestParam("surface") Double surface,
          @RequestParam("price") Double price,
          @RequestParam("description") String description,
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
  public ResponseEntity<?> deleteRental(@PathVariable Long id) {
    rentalService.deleteRental(id);
    return ResponseEntity.ok(Map.of("message", "Rental deleted !"));
  }
}