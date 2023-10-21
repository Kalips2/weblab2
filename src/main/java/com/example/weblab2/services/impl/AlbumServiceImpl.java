package com.example.weblab2.services.impl;

import com.example.weblab2.data.AlbumData;
import com.example.weblab2.dto.AlbumDto;
import com.example.weblab2.entities.Album;
import com.example.weblab2.entities.Artist;
import com.example.weblab2.entities.Label;
import com.example.weblab2.exceptions.Exceptions;
import com.example.weblab2.mappers.AlbumMapper;
import com.example.weblab2.mappers.DataMapper;
import com.example.weblab2.repositories.AlbumRepository;
import com.example.weblab2.repositories.ArtistRepository;
import com.example.weblab2.repositories.LabelRepository;
import com.example.weblab2.services.AlbumService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
  private final AlbumRepository albumRepository;
  private final ArtistRepository artistRepository;
  private final LabelRepository labelRepository;

  @Override
  public List<AlbumDto> getAll() {
    return albumRepository.findAll()
        .stream()
        .map(AlbumMapper::entityToDto)
        .toList();
  }

  @Override
  public AlbumDto getById(Long id) {
    Album album = albumRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException(Exceptions.ALBUM_IS_NOT_FOUND.getMessage()));
    return AlbumMapper.entityToDto(album);
  }

  @Override
  public void create(AlbumData album, MultipartFile file) throws RuntimeException {
    Artist artist = artistRepository
        .findById(album.getArtistId())
        .orElseThrow(() -> new RuntimeException(Exceptions.ARTIST_IS_NOT_FOUND.getMessage()));
    Label label = labelRepository
        .findById(album.getLabelId())
        .orElseThrow(() -> new RuntimeException(Exceptions.LABEL_IS_NOT_FOUND.getMessage()));
    Album savedAlbum = albumRepository.save(AlbumMapper.dataToEntity(album, file, artist, label));
    log.info("Album with id = " + savedAlbum.getId() + " was saved");
  }

  @SneakyThrows
  @Override
  public void update(Long id, AlbumData album, MultipartFile file) throws RuntimeException {
    Album albumToUpdate = albumRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException(Exceptions.ALBUM_IS_NOT_FOUND.getMessage()));
    albumToUpdate.setTitle(album.getTitle());
    albumToUpdate.setReleaseDate(DataMapper.dateFromString(album.getReleaseDate()));
    log.info("Data to update: " + DataMapper.dateFromString(album.getReleaseDate()));
    Artist newArtist = artistRepository
        .findById(album.getArtistId())
        .orElseThrow(() -> new RuntimeException(Exceptions.ARTIST_IS_NOT_FOUND.getMessage()));
    albumToUpdate.setArtist(newArtist);
    Label label = labelRepository
        .findById(album.getLabelId())
        .orElseThrow(() -> new RuntimeException(Exceptions.LABEL_IS_NOT_FOUND.getMessage()));
    albumToUpdate.setLabel(label);
    // TODO: Add storage service to upload MultipartFile
    if (file.getBytes().length != 0) {
      albumToUpdate.setPathToPhoto("");
    }
    albumRepository.save(albumToUpdate);
    log.info("Album with id = " + id + " was updated");
  }

  @Override
  public void delete(Long id) {
    albumRepository.deleteById(id);
    log.info("Album with id = " + id + " was deleted");
  }
}
