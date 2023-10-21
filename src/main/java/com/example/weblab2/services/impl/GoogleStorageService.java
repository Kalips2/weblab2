package com.example.weblab2.services.impl;

import com.example.weblab2.services.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class GoogleStorageService implements StorageService {
  @Override
  public byte[] downloadPhoto(String path) {
    return new byte[0];
  }

  @Override
  public String uploadPhoto(MultipartFile multipartFile) {
    return null;
  }

  @Override
  public void deletePhoto(String path) {

  }
}
