package com.chatop.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageController {
  @GetMapping("/{filename:.+}")
  public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
    Path filePath = Paths.get("uploads", filename);
    if (!Files.exists(filePath)) {
      return ResponseEntity.notFound().build();
    }
    Resource fileResource = new UrlResource(filePath.toUri());
    return ResponseEntity.ok()
            .header("Content-Type", Files.probeContentType(filePath))
            .body(fileResource);
  }
}