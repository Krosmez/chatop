package com.chatop.controller;

import com.chatop.dto.request.RentalRequest;
import com.chatop.entity.Rental;
import com.chatop.service.AuthService;
import com.chatop.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    try {
      Rental rental = rentalService.getRentalById(id);
      return ResponseEntity.ok(rental);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping(consumes = "multipart/form-data")
  public ResponseEntity<?> createRental(
          @RequestParam("name") String name,
          @RequestParam("surface") Double surface,
          @RequestParam("price") Double price,
          @RequestParam("description") String description,
          @RequestParam("picture") MultipartFile picture
  ) {
    try {
      Long ownerId = authService.getCurrentUser().getId();

      String imageUrl = rentalService.handleImageUpload(picture);
      RentalRequest request = new RentalRequest(name, surface, price, imageUrl, description, ownerId);
      rentalService.createRental(request);
      return ResponseEntity.ok(Map.of("message", "Rental created !"));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(Map.of("error", "Erreur lors de l’upload de l’image"));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateRental(@PathVariable Long id, @RequestBody RentalRequest request) {
    try {
      rentalService.updateRental(id, request);
      return ResponseEntity.ok(Map.of("message", "Rental updated !"));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}