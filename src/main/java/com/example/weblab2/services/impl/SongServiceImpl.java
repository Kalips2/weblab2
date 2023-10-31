package com.example.weblab2.services.impl;

import static com.example.weblab2.configuration.CacheConfiguration.LABELS;
import static com.example.weblab2.configuration.CacheConfiguration.SONGS;

import com.example.weblab2.data.SongData;
import com.example.weblab2.dto.SongDto;
import com.example.weblab2.entities.Album;
import com.example.weblab2.entities.Song;
import com.example.weblab2.exceptions.Exceptions;
import com.example.weblab2.exceptions.InternalException;
import com.example.weblab2.mappers.SongMapper;
import com.example.weblab2.repositories.AlbumRepository;
import com.example.weblab2.repositories.SongRepository;
import com.example.weblab2.services.SongService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
  private final SongRepository songRepository;
  private final AlbumRepository albumRepository;

  @Override
  @Cacheable(value = SONGS)
  public List<SongDto> getAll() {
    return songRepository.findAll()
        .stream()
        .map(SongMapper::entityToDto)
        .toList();
  }

  @Override
  @Cacheable(value = SONGS)
  public SongDto getById(Long id) {
    Song song = songRepository
        .findById(id)
        .orElseThrow(() -> new InternalException(Exceptions.SONG_IS_NOT_FOUND));
    return SongMapper.entityToDto(song);
  }

  @Override
  public void create(SongData song) throws RuntimeException {
    Album album = albumRepository
        .findById(song.getAlbum_id())
        .orElseThrow(() -> new InternalException(Exceptions.ALBUM_IS_NOT_FOUND));
    Song savedSong = songRepository.save(SongMapper.dataToEntity(song, album));
    log.info("Song with id = " + savedSong.getId() + " was saved");
  }

  @Override
  public void update(Long id, SongData song) throws RuntimeException {
    Song songToUpdate = songRepository
        .findById(id)
        .orElseThrow(() -> new InternalException(Exceptions.SONG_IS_NOT_FOUND));
    songToUpdate.setTitle(song.getTitle());
    songToUpdate.setDuration(song.getDuration());
    songToUpdate.setGenre(song.getGenre());
    Album album = albumRepository
        .findById(song.getAlbum_id())
        .orElseThrow(() -> new InternalException(Exceptions.ALBUM_IS_NOT_FOUND));
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
