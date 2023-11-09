package com.example.weblab2.services.impl;

import static com.example.weblab2.configuration.CacheConfiguration.LABELS;
import static com.example.weblab2.dto.SearchDto.processSearchDTO;

import com.example.weblab2.data.LabelData;
import com.example.weblab2.dto.LabelDto;
import com.example.weblab2.dto.SearchDto;
import com.example.weblab2.entities.Label;
import com.example.weblab2.exceptions.Exceptions;
import com.example.weblab2.exceptions.InternalException;
import com.example.weblab2.mappers.LabelMapper;
import com.example.weblab2.repositories.LabelRepository;
import com.example.weblab2.services.LabelService;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {
  private final LabelRepository labelRepository;

  @Override
  @Cacheable(value = LABELS)
  public List<LabelDto> getAll() {
    return labelRepository.findAll()
        .stream()
        .map(LabelMapper::entityToDto)
        .toList();
  }

  @Override
  @Cacheable(value = LABELS)
  public List<LabelDto> getAll(SearchDto filter) {
    SearchDto searchDTO = processSearchDTO(filter);
    Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize());
    return labelRepository.findAll(pageable)
        .stream()
        .map(LabelMapper::entityToDto)
        .skip(searchDTO.getSkip())
        .toList();
  }

  @Override
  @Cacheable(value = LABELS)
  public LabelDto getById(Long id) {
    Label label = labelRepository
        .findById(id)
        .orElseThrow(() -> new InternalException(Exceptions.LABEL_IS_NOT_FOUND));
    return LabelMapper.entityToDto(label);
  }

  @Override
  public Long create(LabelData label) throws RuntimeException {
    Label savedLabel = labelRepository.save(LabelMapper.dataToEntity(label));
    log.info("Label with id = " + savedLabel.getId() + " was saved");
    return savedLabel.getId();
  }

  @Override
  public void add(LabelData... labelData) throws RuntimeException {
    Arrays.stream(labelData)
        .forEach(this::create);
    log.info("Labels were added. Amount of them = " + labelData.length);
  }

  @Override
  public void update(Long id, LabelData label) throws RuntimeException {
    Label labelToUpdate = labelRepository
        .findById(id)
        .orElseThrow(() -> new InternalException(Exceptions.LABEL_IS_NOT_FOUND));
    labelToUpdate.setName(label.getName());
    labelToUpdate.setCoordinates(label.getCoordinates());
    labelRepository.save(labelToUpdate);
    log.info("Label with id = " + id + " was updated");
  }

  @Override
  public void delete(Long id) {
    labelRepository.deleteById(id);
    log.info("Label with id = " + id + " was deleted");
  }
}
