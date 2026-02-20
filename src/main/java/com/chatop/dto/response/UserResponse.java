package com.chatop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserResponse {
  private String name;
  private String email;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
