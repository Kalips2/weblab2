package com.example.weblab2.services.impl;

import com.example.weblab2.data.SongData;
import com.example.weblab2.dto.SongDto;
import com.example.weblab2.entities.Album;
import com.example.weblab2.entities.Song;
import com.example.weblab2.exceptions.Exceptions;
import com.example.weblab2.mappers.SongMapper;
import com.example.weblab2.repositories.AlbumRepository;
import com.example.weblab2.repositories.SongRepository;
import com.example.weblab2.services.SongService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
  private final SongRepository songRepository;
  private final AlbumRepository albumRepository;

  @Override
  public List<SongDto> getAll() {
    return songRepository.findAll()
        .stream()
        .map(SongMapper::entityToDto)
        .toList();
  }

  @Override
  public SongDto getById(Long id) {
    Song song = songRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException(Exceptions.SONG_IS_NOT_FOUND.getMessage()));
    return SongMapper.entityToDto(song);
  }

  @Override
  public void create(SongData song) throws RuntimeException {
    Album album = albumRepository
        .findById(song.getAlbum_id())
        .orElseThrow(() -> new RuntimeException(Exceptions.ALBUM_IS_NOT_FOUND.getMessage()));
    Song savedSong = songRepository.save(SongMapper.dataToEntity(song, album));
    log.info("Song with id = " + savedSong.getId() + " was saved");
  }

  @Override
  public void update(Long id, SongData song) throws RuntimeException {
    Song songToUpdate = songRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException(Exceptions.SONG_IS_NOT_FOUND.getMessage()));
    songToUpdate.setTitle(song.getTitle());
    songToUpdate.setDuration(song.getDuration());
    songToUpdate.setGenre(song.getGenre());
    Album album = albumRepository
        .findById(song.getAlbum_id())
        .orElseThrow(() -> new RuntimeException(Exceptions.ALBUM_IS_NOT_FOUND.getMessage()));
    songToUpdate.setAlbum(album);
    songRepository.save(songToUpdate);
    log.info("Song with id = " + id + " was updated");
  }

  @Override
  public void delete(Long id) {
    songRepository.deleteById(id);
    log.info("Song with id = " + id + " was deleted");
  }
}
