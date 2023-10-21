package com.example.weblab2.services;

import com.example.weblab2.data.AlbumData;
import com.example.weblab2.dto.AlbumDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AlbumService {
  List<AlbumDto> getAll();

  AlbumDto getById(Long id);

  void create(AlbumData album, MultipartFile file) throws RuntimeException;

  void update(Long id, AlbumData album, MultipartFile file) throws RuntimeException;

  void delete(Long id);

}
