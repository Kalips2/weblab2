package com.example.weblab2.services.impl;

import static com.example.weblab2.configuration.CacheConfiguration.LABELS;
import static com.example.weblab2.dto.SearchDto.processSearchDTO;

import com.example.weblab2.data.LabelData;
import com.example.weblab2.dto.LabelDto;
import com.example.weblab2.dto.SearchDto;
import com.example.weblab2.elastic.dto.LabelElasticDto;
import com.example.weblab2.elastic.repositories.LabelElasticRepository;
import com.example.weblab2.entities.Label;
import com.example.weblab2.exceptions.Exceptions;
import com.example.weblab2.exceptions.InternalException;
import com.example.weblab2.mappers.LabelMapper;
import com.example.weblab2.repositories.AlbumRepository;
import com.example.weblab2.repositories.LabelRepository;
import com.example.weblab2.services.LabelService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {
  private final LabelRepository labelRepository;
  private final LabelElasticRepository labelElasticRepository;
  private final AlbumRepository albumRepository;

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

  @Override
  public Long getAmountOfAlbums(Long id) {
    return albumRepository.countAlbumsByLabelId(id);
  }

  @Override
  public Optional<LabelDto> getByName(String name) {
    return labelRepository.findByName(name).map(LabelMapper::entityToDto);
  }

  @Override
  public List<String> getNamesThatContain(String name) {
    return labelRepository.findAll()
        .stream()
        .map(Label::getName)
        .filter(l->l.contains(name))
        .toList();
  }

  @Override
  public Page<LabelElasticDto> getAllElastic(SearchDto searchDto) {
    log.info("[Elastic] Get all labels");
    return labelElasticRepository.findAll(PageRequest.of(searchDto.getPage(), searchDto.getSize()));
  }

  @Override
  public Page<LabelElasticDto> getByNameElastic(String name, SearchDto searchDto) {
    return labelElasticRepository.findByName(name, PageRequest.of(searchDto.getPage(), searchDto.getSize()));
  }

  @Override
  public LabelElasticDto getByIdElastic(String id) {
    return labelElasticRepository.findById(id)
        .orElseThrow(() -> new InternalException(Exceptions.ELASTIC_LABEL_IS_NOT_FOUND));
  }

  @Transactional
  @Override
  public void createElastic(LabelData labelData) {
    LabelElasticDto labelElasticDto = LabelMapper.entityToElasticDto(labelData);
    LabelElasticDto saved = labelElasticRepository.save(labelElasticDto);
    log.info("[Elastic] Label with id: " + saved.getId() + " created");
  }

  @Override
  @Transactional
  public void updateElastic(String id, LabelData labelData) {
    LabelElasticDto toUpdate = getByIdElastic(id);
    toUpdate.setName(labelData.getName());
    toUpdate.setCoordinates(labelData.getCoordinates().toString());
    labelElasticRepository.save(toUpdate);
    log.info("[Elastic] Label with id = " + id + " was updated");
  }

  @Override
  @Transactional
  public void deleteElastic(String id) {
    labelElasticRepository.delete(getByIdElastic(id));
    log.info("[Elastic] Label with id = " + id + " was deleted");
  }
}
