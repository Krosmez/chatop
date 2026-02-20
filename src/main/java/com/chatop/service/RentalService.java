package com.chatop.service;

import com.chatop.dto.request.RentalRequest;
import com.chatop.entity.Rental;
import com.chatop.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {
  private final RentalRepository rentalRepository;

  public List<Rental> getAllRentals() {
    return rentalRepository.findAll();
  }

  public Rental getRentalById(Long id) {
    return rentalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rental not found"));
  }

  public void createRental(RentalRequest request) {
    Rental rental = new Rental();
    rental.setName(request.getName());
    rental.setSurface(request.getSurface());
    rental.setPrice(request.getPrice());
    rental.setPicture(request.getPicture());
    rental.setDescription(request.getDescription());
    rental.setOwnerId(request.getOwnerId());
    rental.setCreatedAt(LocalDateTime.now());
    rental.setUpdatedAt(LocalDateTime.now());
    rentalRepository.save(rental);
  }

  public Rental updateRental(Long id, RentalRequest request) {
    Rental rental = rentalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rental not found"));
    rental.setName(request.getName());
    rental.setSurface(request.getSurface());
    rental.setPrice(request.getPrice());
    rental.setPicture(request.getPicture());
    rental.setDescription(request.getDescription());
    rental.setOwnerId(request.getOwnerId());
    rental.setUpdatedAt(LocalDateTime.now());
    return rentalRepository.save(rental);
  }

  public String handleImageUpload(MultipartFile picture) throws IOException {
    if (picture == null || picture.isEmpty()) {
      throw new IllegalArgumentException("Aucune image fournie.");
    }
    String originalFilename = picture.getOriginalFilename();
    if (originalFilename == null ||
            !(originalFilename.toLowerCase().endsWith(".jpg") ||
                    originalFilename.toLowerCase().endsWith(".jpeg") ||
                    originalFilename.toLowerCase().endsWith(".png"))) {
      throw new IllegalArgumentException("Seuls les fichiers JPG, JPEG et PNG sont acceptés.");
    }
    String uploadsDir = "uploads/";
    Files.createDirectories(Paths.get(uploadsDir));
    String filename = System.currentTimeMillis() + "_" + originalFilename;
    Path filePath = Paths.get(uploadsDir, filename);
    Files.copy(picture.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    return "/api/images/" + filename;
  }
}