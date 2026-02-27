package com.chatop.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entité représentant une location immobilière")
public class Rental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID unique de la location", example = "1")
  private Long id;

  @Schema(description = "Nom de la location", example = "Villa vue mer")
  private String name;

  @Schema(description = "Surface en m²", example = "120.5")
  private Double surface;

  @Schema(description = "Prix par nuit en euros", example = "250.0")
  private Double price;

  @Schema(description = "URL de l'image principale", example = "http://localhost:8080/api/images/1771688173705_gal" +
      "-yosef-01.jpg")
  private String picture;

  @Schema(description = "Description détaillée de la location", example = "Belle villa avec vue sur la mer...")
  private String description;

  @Column(name = "owner_id")
  @Schema(description = "ID du propriétaire", example = "1")
  private Long ownerId;

  @Schema(description = "Date de création", example = "2026-02-21T10:30:00")
  private LocalDateTime created_at;

  @Schema(description = "Date de dernière modification", example = "2026-02-21T10:30:00")
  private LocalDateTime updated_at;
}