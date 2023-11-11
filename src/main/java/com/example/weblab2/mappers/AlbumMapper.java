package com.example.weblab2.mappers;

import com.example.weblab2.data.AlbumData;
import com.example.weblab2.dto.AlbumDto;
import com.example.weblab2.elastic.dto.AlbumElasticDto;
import com.example.weblab2.entities.Album;
import com.example.weblab2.entities.Artist;
import com.example.weblab2.entities.Label;
import com.example.weblab2.services.impl.GoogleStorageService;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@UtilityClass
public class AlbumMapper {
  private final GoogleStorageService storageService = new GoogleStorageService();

  @SneakyThrows
  public AlbumDto entityToDto(Album album) {
    return AlbumDto.builder()
        .id(album.getId())
        .title(album.getTitle())
        .releaseDate(album.getReleaseDate())
        .artist(ArtistMapper.entityToDto(album.getArtist()))
        .label(LabelMapper.entityToDto(album.getLabel()))
        .photo(storageService.downloadPhoto(album.getPathToPhoto()))
        .build();
  }

  public Album dataToEntity(AlbumData album, MultipartFile file, Artist artist, Label label) {
    return Album.builder()
        .title(album.getTitle())
        .releaseDate(DataMapper.dateFromString(album.getReleaseDate()))
        .artist(artist)
        .label(label)
        .pathToPhoto(storageService.uploadPhoto(file))
        .build();
  }

  public AlbumElasticDto entityToElasticDto(Album album) {
    return AlbumElasticDto.builder()
        .id(album.getId())
        .title(album.getTitle())
        .releaseDate(album.getReleaseDate())
        .build();
  }

}
