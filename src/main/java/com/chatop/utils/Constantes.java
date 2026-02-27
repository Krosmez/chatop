package com.chatop.utils;

/**
 * Constantes de l'application
 * Inclut les exemples Swagger pour la documentation API
 */
public class Constantes {
  // ==================== SWAGGER EXAMPLES - AUTH ====================

  public static final String AUTH_REGISTER_SUCCESS = """
      {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImlhdCI6MTcwODYwMDAwMCwiZXhwIjoxNzA4Njg2NDAwfQ.xyz123"
      }
      """;

  public static final String AUTH_REGISTER_ERROR_400 = """
      {
        "error": "Le nom d'utilisateur est déjà utilisé",
        "status": 400,
        "timestamp": "2026-02-22T10:30:00"
      }
      """;

  public static final String AUTH_LOGIN_SUCCESS = """
      {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzA4NjAwMDAwLCJleHAiOjE3MDg2ODY0MDB9.abc456"
      }
      """;

  public static final String AUTH_LOGIN_ERROR_401 = """
      {
        "error": "Email ou mot de passe incorrect",
        "status": 401,
        "timestamp": "2026-02-22T10:30:00"
      }
      """;

  public static final String AUTH_ME_SUCCESS = """
      {
        "name": "John Doe",
        "email": "john.doe@example.com",
        "created_at": "2026-01-15T10:30:00",
        "updated_at": "2026-02-20T14:20:00"
      }
      """;
  // ==================== SWAGGER EXAMPLES - USER ====================

  public static final String USER_GET_BY_ID_SUCCESS = """
      {
        "name": "John Doe",
        "email": "john.doe@example.com",
        "created_at": "2026-01-15T10:30:00",
        "updated_at": "2026-02-20T14:20:00"
      }
      """;

  public static final String USER_NOT_FOUND_ERROR_404 = """
      {
        "error": "Utilisateur non trouvé",
        "status": 404,
        "timestamp": "2026-02-22T10:30:00"
      }
      """;
  // ==================== SWAGGER EXAMPLES - RENTAL ====================

  public static final String RENTAL_LIST_SUCCESS = """
      {
        "rentals": [{
          "id": 1,
          "name": "Villa vue mer",
          "surface": 120.5,
          "price": 250.0,
          "picture": "http://localhost:8080/api/images/1771688173705_villa.jpg",
          "description": "Belle villa avec vue sur la mer",
          "ownerId": 1,
          "created_at": "2026-01-15T10:30:00",
          "updated_at": "2026-02-20T14:20:00"
        }]
      }
      """;

  public static final String RENTAL_GET_BY_ID_SUCCESS = """
      {
        "id": 1,
        "name": "Villa vue mer",
        "surface": 120.5,
        "price": 250.0,
        "picture": "http://localhost:8080/api/images/1771688173705_villa.jpg",
        "description": "Belle villa avec vue sur la mer",
        "ownerId": 1,
        "created_at": "2026-01-15T10:30:00",
        "updated_at": "2026-02-20T14:20:00"
      }
      """;

  public static final String RENTAL_NOT_FOUND_ERROR_404 = """
      {
        "error": "Rental not found",
        "status": 404,
        "timestamp": "2026-02-22T10:30:00"
      }
      """;

  public static final String RENTAL_CREATED_SUCCESS = """
      {
        "message": "Rental created !"
      }
      """;

  public static final String RENTAL_IMAGE_REQUIRED_ERROR_400 = """
      {
        "error": "L'image est requise",
        "status": 400,
        "timestamp": "2026-02-22T10:30:00"
      }
      """;

  public static final String RENTAL_UPDATED_SUCCESS = """
      {
        "message": "Rental updated !"
      }
      """;

  public static final String RENTAL_INVALID_DATA_ERROR_400 = """
      {
        "error": "Données invalides",
        "status": 400,
        "timestamp": "2026-02-22T10:30:00"
      }
      """;

  public static final String RENTAL_DELETED_SUCCESS = """
      {
        "message": "Rental deleted !"
      }
      """;

  public static final String RENTAL_UPLOAD_ERROR_500 = """
      {
        "error": "Erreur lors de l'upload de l'image",
        "status": 500,
        "timestamp": "2026-02-22T10:30:00"
      }
      """;
  // ==================== SWAGGER EXAMPLES - MESSAGE ====================

  public static final String MESSAGE_SENT_SUCCESS = """
      {
        "message": "Message send with success"
      }
      """;

  public static final String MESSAGE_EMPTY_ERROR_400 = """
      {
        "error": "Le message ne peut pas être vide",
        "status": 400,
        "timestamp": "2026-02-22T10:30:00"
      }
      """;

  public static final String MESSAGE_RENTAL_ID_MISSING_ERROR_400 = """
      {
        "error": "L'id de la location est obligatoire",
        "status": 400,
        "timestamp": "2026-02-22T10:30:00"
      }
      """;

  public static final String MESSAGE_REQUEST_EXAMPLE = """
      {
        "rental_id": 1,
        "message": "Je suis intéressé par cette location"
      }
      """;

  public static final String MESSAGE_REQUEST_SIMPLE_EXAMPLE = """
      {
        "message": "Je suis intéressé par cette location"
      }
      """;
  // ==================== SWAGGER EXAMPLES - IMAGE ====================

  public static final String IMAGE_NOT_FOUND_ERROR_404 = """
      {
        "error": "Image not found",
        "status": 404,
        "timestamp": "2026-02-22T10:30:00"
      }
      """;
  // ==================== SWAGGER EXAMPLES - COMMON ERRORS ====================

  public static final String UNAUTHORIZED_ERROR_401 = """
      {
        "error": "Utilisateur non authentifié",
        "status": 401,
        "timestamp": "2026-02-22T10:30:00"
      }
      """;
}
