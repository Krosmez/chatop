package com.chatop.dto.request;

import lombok.Data;

@Data
public class MessageRequest {
  private Long user_id;
  private Long rental_id;
  private String message;
}

