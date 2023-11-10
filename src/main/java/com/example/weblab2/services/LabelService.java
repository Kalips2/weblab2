package com.example.weblab2.services;

import com.example.weblab2.data.LabelData;
import com.example.weblab2.dto.LabelDto;
import com.example.weblab2.dto.SearchDto;
import java.util.List;
import java.util.Optional;

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

}
