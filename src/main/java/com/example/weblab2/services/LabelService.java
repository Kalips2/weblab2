package com.example.weblab2.services;

import com.example.weblab2.data.LabelData;
import com.example.weblab2.dto.LabelDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface LabelService {
  List<LabelDto> getAll();

  LabelDto getById(Long id);

  void create(LabelData label) throws RuntimeException;

  void update(Long id, LabelData label) throws RuntimeException;

  void delete(Long id);

}
