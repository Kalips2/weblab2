package com.example.weblab2.mappers;

import com.example.weblab2.data.ArtistData;
import com.example.weblab2.dto.ArtistDto;
import com.example.weblab2.elastic.dto.AlbumElasticDto;
import com.example.weblab2.elastic.dto.ArtistElasticDto;
import com.example.weblab2.entities.Album;
import com.example.weblab2.entities.Artist;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ArtistMapper {
  public ArtistDto entityToDto(Artist artist) {
    return ArtistDto.builder()
        .id(artist.getId())
        .name(artist.getName())
        .surname(artist.getSurname())
        .dateOfBirth(artist.getDateOfBirth())
        .build();
  }

  public Artist dataToEntity(ArtistData artist) {
    return Artist.builder()
        .name(artist.getName())
        .surname(artist.getSurname())
        .dateOfBirth(DataMapper.dateFromString(artist.getDateOfBirth()))
        .build();
  }

  public ArtistElasticDto entityToElasticDto(Artist artist) {
    return ArtistElasticDto.builder()
        .id(artist.getId())
        .name(artist.getName())
        .surname(artist.getSurname())
        .dateOfBirth(artist.getDateOfBirth())
        .build();
  }
}
