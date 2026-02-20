package com.chatop.entity;

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
public class Rental {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Double surface;
  private Double price;
  private String picture;
  private String description;

  @Column(name = "owner_id")
  private Long ownerId;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}