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
    messageService.sendMessage(request);
    return ResponseEntity.ok(Map.of("message", "Message send with success"));
  }

  @PostMapping("/rental/{rentalId}")
  public ResponseEntity<?> sendMessageToRental(@PathVariable Long rentalId, @RequestBody MessageRequest request) {
    request.setRental_id(rentalId);
    messageService.sendMessage(request);
    return ResponseEntity.ok(Map.of("message", "Message send with success"));
  }
}
