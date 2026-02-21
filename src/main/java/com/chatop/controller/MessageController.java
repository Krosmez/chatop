package com.chatop.controller;

import com.chatop.dto.request.MessageRequest;
import com.chatop.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
  private final MessageService messageService;

  @PostMapping
  public ResponseEntity<?> sendMessage(@RequestBody MessageRequest request) {
    try {
      messageService.sendMessage(request);
      return ResponseEntity.ok(Map.of("message", "Message send with success"));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    } catch (RuntimeException e) {
      return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
    }
  }

  @PostMapping("/rental/{rentalId}")
  public ResponseEntity<?> sendMessage(@PathVariable Long rentalId, @RequestBody MessageRequest request) {
    try {
      request.setRental_id(rentalId);
      messageService.sendMessage(request);
      return ResponseEntity.ok(Map.of("message", "Message send with success"));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    } catch (RuntimeException e) {
      return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
    }
  }
}
