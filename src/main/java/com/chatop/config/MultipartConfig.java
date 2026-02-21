package com.chatop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
public class MultipartConfig {

  @Value("${spring.servlet.multipart.location}")
  private String uploadTempDir;

  @PostConstruct
  public void init() {
    File tempDir = new File(uploadTempDir);
    if (!tempDir.exists()) {
      tempDir.mkdirs();
    }
  }
}
