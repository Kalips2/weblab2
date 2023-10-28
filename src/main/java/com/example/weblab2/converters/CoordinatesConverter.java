package com.example.weblab2.converters;

import com.example.weblab2.dto.CoordinateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Converter
@RequiredArgsConstructor
public class CoordinatesConverter implements AttributeConverter<CoordinateDto, String> {
  private final ObjectMapper objectMapper;

  @SneakyThrows
  @Override
  public String convertToDatabaseColumn(CoordinateDto attribute) {
    return attribute != null
        ? objectMapper.writeValueAsString(attribute)
        : null;
  }

  @SneakyThrows
  @Override
  public CoordinateDto convertToEntityAttribute(String dbData) {
    return dbData != null
        ? objectMapper.readValue(dbData, CoordinateDto.class)
        : null;
  }
}
