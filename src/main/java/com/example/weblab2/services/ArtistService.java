package com.example.weblab2.services;

import com.example.weblab2.data.ArtistData;
import com.example.weblab2.dto.ArtistDto;
import java.util.List;

public interface ArtistService {
  List<ArtistDto> getAll();

  ArtistDto getById(Long id);

  void create(ArtistData artist) throws RuntimeException;

  void update(Long id, ArtistData artist) throws RuntimeException;

  void delete(Long id);

}
