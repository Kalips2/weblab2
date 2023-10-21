package com.example.weblab2.mappers;

import com.example.weblab2.data.SongData;
import com.example.weblab2.dto.AlbumDto;
import com.example.weblab2.dto.SongDto;
import com.example.weblab2.entities.Album;
import com.example.weblab2.entities.Song;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SongMapper {
  public SongDto entityToDto(Song song) {
    return SongDto.builder()
        .id(song.getId())
        .title(song.getTitle())
        .duration(song.getDuration())
        .genre(song.getGenre())
        .album(AlbumMapper.entityToDto(song.getAlbum()))
        .build();
  }

  public Song dataToEntity(SongData song, Album album) {
    return Song.builder()
        .title(song.getTitle())
        .duration(song.getDuration())
        .genre(song.getGenre())
        .album(album)
        .build();
  }
}
