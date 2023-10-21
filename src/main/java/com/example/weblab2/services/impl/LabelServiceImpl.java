package com.example.weblab2.services.impl;

import com.example.weblab2.data.AlbumData;
import com.example.weblab2.data.LabelData;
import com.example.weblab2.dto.AlbumDto;
import com.example.weblab2.dto.LabelDto;
import com.example.weblab2.entities.Album;
import com.example.weblab2.entities.Artist;
import com.example.weblab2.entities.Label;
import com.example.weblab2.exceptions.Exceptions;
import com.example.weblab2.mappers.AlbumMapper;
import com.example.weblab2.mappers.DataMapper;
import com.example.weblab2.mappers.LabelMapper;
import com.example.weblab2.repositories.AlbumRepository;
import com.example.weblab2.repositories.ArtistRepository;
import com.example.weblab2.repositories.LabelRepository;
import com.example.weblab2.services.AlbumService;
import com.example.weblab2.services.LabelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {
  private final LabelRepository labelRepository;

  @Override
  public List<LabelDto> getAll() {
    return labelRepository.findAll()
        .stream()
        .map(LabelMapper::entityToDto)
        .toList();
  }

  @Override
  public LabelDto getById(Long id) {
    Label label = labelRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException(Exceptions.LABEL_IS_NOT_FOUND.getMessage()));
    return LabelMapper.entityToDto(label);
  }

  @Override
  public void create(LabelData label) throws RuntimeException {
    Label savedLabel = labelRepository.save(LabelMapper.dataToEntity(label));
    log.info("Label with id = " + savedLabel.getId() + " was saved");
  }

  @Override
  public void update(Long id, LabelData label) throws RuntimeException {
    Label labelToUpdate = labelRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException(Exceptions.LABEL_IS_NOT_FOUND.getMessage()));
    labelToUpdate.setName(label.getName());
    labelRepository.save(labelToUpdate);
    log.info("Label with id = " + id + " was updated");
  }

  @Override
  public void delete(Long id) {
    labelRepository.deleteById(id);
    log.info("Label with id = " + id + " was deleted");
  }
}
