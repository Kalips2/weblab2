package com.example.weblab2.services.impl;

import com.example.weblab2.services.StorageService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class GoogleStorageService implements StorageService {

  private final Storage storage;

  private final String BUCKET = "weblab2-knu";

  @SneakyThrows
  public GoogleStorageService() {
    GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
    storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
  }

  @Override
  public byte[] downloadPhoto(String path) throws FileNotFoundException {
    Blob blob = storage.get(BlobId.of(BUCKET, path));
    if (blob != null) {
      log.info("Photo was downloaded by " + path);
      return blob.getContent();
    } else {
      throw new FileNotFoundException("Blob with path " + path + " not found in the bucket.");
    }
  }

  @Override
  public String uploadPhoto(MultipartFile multipartFile) {
    try {
      String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
      BlobId blobId = BlobId.of(BUCKET, fileName);
      BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
      byte[] content = multipartFile.getBytes();
      Blob blob = storage.create(blobInfo, content);
      return blob.getName();
    } catch (IOException e) {
      throw new StorageException(-1, "Failed to store file", e);
    }
  }

  @Override
  public void deletePhoto(String path) {
    storage.delete(BlobId.of(BUCKET, path));
    log.info("Photo was deleted from " + path);
  }
}
