package com.chatop.repository;

import com.chatop.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {
  Optional<Rental> findById(Long id);
}
