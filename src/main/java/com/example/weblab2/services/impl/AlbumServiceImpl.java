package com.example.weblab2.services.impl;

import static com.example.weblab2.configuration.CacheConfiguration.ALBUMS;

import com.example.weblab2.data.AlbumData;
import com.example.weblab2.dto.AlbumDto;
import com.example.weblab2.entities.Album;
import com.example.weblab2.entities.Artist;
import com.example.weblab2.entities.Label;
import com.example.weblab2.exceptions.Exceptions;
import com.example.weblab2.exceptions.InternalException;
import com.example.weblab2.mappers.AlbumMapper;
import com.example.weblab2.mappers.DataMapper;
import com.example.weblab2.repositories.AlbumRepository;
import com.example.weblab2.repositories.ArtistRepository;
import com.example.weblab2.repositories.LabelRepository;
import com.example.weblab2.services.AlbumService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
  private final AlbumRepository albumRepository;
  private final ArtistRepository artistRepository;
  private final LabelRepository labelRepository;
  private final GoogleStorageService googleStorageService;

  @Override
  @Cacheable(value = ALBUMS)
  public List<AlbumDto> getAll() {
    return albumRepository.findAll()
        .stream()
        .map(AlbumMapper::entityToDto)
        .toList();
  }

  @Override
  @Cacheable(value = ALBUMS)
  public AlbumDto getById(Long id) {
    Album album = albumRepository
        .findById(id)
        .orElseThrow(() -> new InternalException(Exceptions.ALBUM_IS_NOT_FOUND));
    return AlbumMapper.entityToDto(album);
  }

  @Override
  public void create(AlbumData album, MultipartFile file) throws RuntimeException {
    Artist artist = artistRepository
        .findById(album.getArtistId())
        .orElseThrow(() -> new InternalException(Exceptions.ARTIST_IS_NOT_FOUND));
    Label label = labelRepository
        .findById(album.getLabelId())
        .orElseThrow(() -> new InternalException(Exceptions.LABEL_IS_NOT_FOUND));
    Album savedAlbum = albumRepository.save(AlbumMapper.dataToEntity(album, file, artist, label));
    log.info("Album with id = " + savedAlbum.getId() + " was saved");
  }

  @SneakyThrows
  @Override
  public void update(Long id, AlbumData album, MultipartFile file) throws RuntimeException {
    Album albumToUpdate = albumRepository
        .findById(id)
        .orElseThrow(() -> new InternalException(Exceptions.ALBUM_IS_NOT_FOUND));
    albumToUpdate.setTitle(album.getTitle());

    albumToUpdate.setReleaseDate(DataMapper.dateFromString(album.getReleaseDate()));

    Artist newArtist = artistRepository
        .findById(album.getArtistId())
        .orElseThrow(() -> new InternalException(Exceptions.ARTIST_IS_NOT_FOUND));
    albumToUpdate.setArtist(newArtist);

    Label label = labelRepository
        .findById(album.getLabelId())
        .orElseThrow(() -> new InternalException(Exceptions.LABEL_IS_NOT_FOUND));
    albumToUpdate.setLabel(label);

    // Photo uploading
    if (file.getBytes().length != 0) {
      String photoPath = albumToUpdate.getPathToPhoto();
      String newPhotoPath;
      if (!Objects.isNull(photoPath) && !photoPath.isEmpty()) {
        googleStorageService.deletePhoto(photoPath);
      }
      newPhotoPath = googleStorageService.uploadPhoto(file);
      albumToUpdate.setPathToPhoto(newPhotoPath);
    }

    albumRepository.save(albumToUpdate);
    log.info("Album with id = " + id + " was updated");
  }

  @Override
  public void delete(Long id) {
    Album albumToDelete = albumRepository
        .findById(id)
        .orElseThrow(() -> new InternalException(Exceptions.ALBUM_IS_NOT_FOUND));
    albumRepository.delete(albumToDelete);
    googleStorageService.deletePhoto(albumToDelete.getPathToPhoto());
    log.info("Album with id = " + id + " was deleted");
  }
}
