package com.example.weblab2.services;

import com.example.weblab2.data.SongData;
import com.example.weblab2.dto.SongDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface SongService {
  List<SongDto> getAll();

  SongDto getById(Long id);

  void create(SongData song) throws RuntimeException;

  void update(Long id, SongData song) throws RuntimeException;

  void delete(Long id);

}
