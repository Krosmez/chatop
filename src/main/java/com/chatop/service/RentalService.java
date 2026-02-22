package com.chatop.service;

import com.chatop.dto.request.RentalRequest;
import com.chatop.entity.Rental;
import com.chatop.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    rental.setCreated_at(LocalDateTime.now());
    rental.setUpdated_at(LocalDateTime.now());
    rentalRepository.save(rental);
  }

  public Rental updateRental(Long id, RentalRequest request) {
    Rental rental = rentalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rental not found"));
    rental.setName(request.getName());
    rental.setSurface(request.getSurface());
    rental.setPrice(request.getPrice());
    rental.setDescription(request.getDescription());
    rental.setOwnerId(request.getOwnerId());
    rental.setUpdated_at(LocalDateTime.now());
    return rentalRepository.save(rental);
  }

  public void deleteRental(Long id) {
    Rental rental = rentalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rental not found"));
    rentalRepository.delete(rental);
  }
}