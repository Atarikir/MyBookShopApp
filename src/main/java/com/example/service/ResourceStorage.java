package com.example.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ResourceStorage {

  @Value("${upload.path}")
  String uploadPath;

  public String saveNewBookImage(MultipartFile file, String slug) throws IOException {
    String resourceURI = null;
    if (!file.isEmpty()) {
      if (!new File(uploadPath).exists()) {
        Files.createDirectories(Paths.get(uploadPath));
        log.info("created image folder in " + uploadPath, this.getClass().getSimpleName());
      }

      log.info("slug - " + slug);

      String fileName = slug + "." + FilenameUtils.getExtension(file.getOriginalFilename());
      Path path = Paths.get(uploadPath, fileName);
      resourceURI = "/book-covers/" + fileName;
      file.transferTo(path); //uploading user file here
      log.info(fileName + " uploaded OK!", this.getClass().getSimpleName());
    }

    return resourceURI;
  }
}
