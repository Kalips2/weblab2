package com.example.weblab2.services;

import java.io.FileNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
  byte[] downloadPhoto(String path) throws FileNotFoundException;

  String uploadPhoto(MultipartFile multipartFile);

  void deletePhoto(String path);
}
