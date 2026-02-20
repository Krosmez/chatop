package com.chatop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class RentalRequest {
  private String name;
  private Double surface;
  private Double price;
  private String picture;
  private String description;
  private Long ownerId;
}
