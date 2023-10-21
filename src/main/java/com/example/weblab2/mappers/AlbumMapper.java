package com.example.weblab2.mappers;

import com.example.weblab2.data.AlbumData;
import com.example.weblab2.dto.AlbumDto;
import com.example.weblab2.entities.Album;
import com.example.weblab2.entities.Artist;
import com.example.weblab2.entities.Label;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

@UtilityClass
public class AlbumMapper {

  public AlbumDto entityToDto(Album album) {
    return AlbumDto.builder()
        .id(album.getId())
        .title(album.getTitle())
        .releaseDate(album.getReleaseDate())
        .artist(ArtistMapper.entityToDto(album.getArtist()))
        .label(LabelMapper.entityToDto(album.getLabel()))
        // TODO: add photo from storage ??
        .photo(new byte[] {})
        .build();
  }

  public Album dataToEntity(AlbumData album, MultipartFile file, Artist artist, Label label) {
    return Album.builder()
        .title(album.getTitle())
        .releaseDate(DataMapper.dateFromString(album.getReleaseDate()))
        .artist(artist)
        .label(label)
        // TODO: uploading to storage
        .pathToPhoto("")
        .build();
  }
}
