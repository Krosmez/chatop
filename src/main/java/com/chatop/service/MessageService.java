package com.chatop.service;

import com.chatop.dto.request.MessageRequest;
import com.chatop.entity.Message;
import com.chatop.exception.BadRequestException;
import com.chatop.exception.UnauthorizedException;
import com.chatop.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
  private final MessageRepository messageRepository;
  private final AuthService authService;
  private final RentalService rentalService;

  public void sendMessage(MessageRequest request) {
    if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
      throw new BadRequestException("Le message ne peut pas être vide");
    }
    Long userId;
    try {
      userId = authService.getCurrentUser().getId();
    } catch (Exception e) {
      throw new UnauthorizedException("Utilisateur non authentifié");
    }
    if (request.getRental_id() == null) {
      throw new BadRequestException("L'id de la location est obligatoire");
    }
    rentalService.getRentalById(request.getRental_id());
    Message message = new Message();
    message.setUserId(userId);
    message.setRentalId(request.getRental_id());
    message.setMessage(request.getMessage());
    messageRepository.save(message);
  }
}
