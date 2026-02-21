package com.chatop.service;

import com.chatop.dto.request.MessageRequest;
import com.chatop.entity.Message;
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
      throw new IllegalArgumentException("Le message ne peut pas être vide.");
    }
    // Récupération du user connecté
    Long userId;
    try {
      userId = authService.getCurrentUser().getId();
    } catch (Exception e) {
      throw new RuntimeException("Utilisateur non authentifié");
    }
    // Vérification du rental_id
    if (request.getRental_id() == null) {
      throw new IllegalArgumentException("L'id de la location est obligatoire.");
    }
    // Vérification que la location existe
    try {
      rentalService.getRentalById(request.getRental_id());
    } catch (RuntimeException e) {
      throw new IllegalArgumentException("La location spécifiée n'existe pas.");
    }
    Message message = new Message();
    message.setUserId(userId);
    message.setRentalId(request.getRental_id());
    message.setMessage(request.getMessage());
    messageRepository.save(message);
  }
}
