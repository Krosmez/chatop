package com.chatop.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageUtils {

  public static String handleImageUpload(MultipartFile picture) throws IOException {
    if(picture == null || picture.isEmpty()) {
      return null;
    }
    String uploadDir = "uploads/";
    File directory = new File(uploadDir);
    if(!directory.exists()) {
      directory.mkdirs();
    }
    String filename = System.currentTimeMillis() + "_" + picture.getOriginalFilename();
    Path filePath = Paths.get(uploadDir + filename);
    try(InputStream inputStream = picture.getInputStream()) {
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    }
    return "http://localhost:8080/api/images/" + filename; // Retourner l'URL complète
  }
}
