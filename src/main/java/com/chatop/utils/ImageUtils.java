package com.chatop.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageUtils {
  public static String handleImageUpload(MultipartFile picture) throws IOException {
    if (picture == null || picture.isEmpty()) {
      throw new IllegalArgumentException("Aucune image fournie.");
    }
    String originalFilename = picture.getOriginalFilename();
    if (originalFilename == null ||
            !(originalFilename.toLowerCase().endsWith(".jpg") ||
                    originalFilename.toLowerCase().endsWith(".jpeg") ||
                    originalFilename.toLowerCase().endsWith(".png"))) {
      throw new IllegalArgumentException("Seuls les fichiers JPG, JPEG et PNG sont acceptés.");
    }
    String uploadsDir = "uploads/";
    Files.createDirectories(Paths.get(uploadsDir));
    String filename = System.currentTimeMillis() + "_" + originalFilename;
    Path filePath = Paths.get(uploadsDir, filename);
    Files.copy(picture.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    return "/api/images/" + filename;
  }
}
