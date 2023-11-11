package com.example.weblab2.services;

import com.example.weblab2.data.LabelData;
import com.example.weblab2.dto.LabelDto;
import com.example.weblab2.dto.SearchDto;
import com.example.weblab2.elastic.dto.LabelElasticDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface LabelService {
  List<LabelDto> getAll();

  List<LabelDto> getAll(SearchDto filter);

  LabelDto getById(Long id);

  Long create(LabelData label) throws RuntimeException;

  void add(LabelData... labelData) throws RuntimeException;

  void update(Long id, LabelData label) throws RuntimeException;

  void delete(Long id);

  Long getAmountOfAlbums(Long id);

  Optional<LabelDto> getByName(String name);

  List<String> getNamesThatContain(String name);

  Page<LabelElasticDto> getAllElastic(SearchDto searchDto);

  Page<LabelElasticDto> getByNameElastic(String name, SearchDto searchDto);

  LabelElasticDto getByIdElastic(String id);

  void createElastic(LabelData labelData);

  void updateElastic(String id, LabelData labelData);

  void deleteElastic(String id);

}
