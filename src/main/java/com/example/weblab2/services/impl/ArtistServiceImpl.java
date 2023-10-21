package com.example.weblab2.services.impl;

import com.example.weblab2.data.ArtistData;
import com.example.weblab2.dto.ArtistDto;
import com.example.weblab2.entities.Artist;
import com.example.weblab2.exceptions.Exceptions;
import com.example.weblab2.mappers.ArtistMapper;
import com.example.weblab2.mappers.DataMapper;
import com.example.weblab2.repositories.AlbumRepository;
import com.example.weblab2.repositories.ArtistRepository;
import com.example.weblab2.repositories.LabelRepository;
import com.example.weblab2.services.ArtistService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {
  private final ArtistRepository artistRepository;

  @Override
  public List<ArtistDto> getAll() {
    return artistRepository.findAll()
        .stream()
        .map(ArtistMapper::entityToDto)
        .toList();
  }

  @Override
  public ArtistDto getById(Long id) {
    Artist artist = artistRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException(Exceptions.ARTIST_IS_NOT_FOUND.getMessage()));
    return ArtistMapper.entityToDto(artist);
  }

  @Override
  public void create(ArtistData artist) throws RuntimeException {
    Artist savedArtist = artistRepository.save(ArtistMapper.dataToEntity(artist));
    log.info("Artist with id = " + savedArtist.getId() + " was saved");
  }

  @Override
  public void update(Long id, ArtistData artist) throws RuntimeException {
    Artist artistToUpdate = artistRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException(Exceptions.ARTIST_IS_NOT_FOUND.getMessage()));
    artistToUpdate.setName(artist.getName());
    artistToUpdate.setSurname(artist.getSurname());
    artistToUpdate.setDateOfBirth(DataMapper.dateFromString(artist.getDateOfBirth()));
    artistRepository.save(artistToUpdate);
    log.info("Artist with id = " + id + " was updated");
  }

  @Override
  public void delete(Long id) {
    artistRepository.deleteById(id);
    log.info("Artist with id = " + id + " was deleted");
  }
}
