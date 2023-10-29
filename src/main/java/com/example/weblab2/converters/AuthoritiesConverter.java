package com.example.weblab2.converters;

import com.example.weblab2.dto.security.AuthorityDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Converter
@RequiredArgsConstructor
public class AuthoritiesConverter implements AttributeConverter<Set<AuthorityDto>, String> {
  private final ObjectMapper objectMapper;

  @SneakyThrows
  @Override
  public String convertToDatabaseColumn(Set<AuthorityDto> attribute) {
    return attribute != null
        ? objectMapper.writeValueAsString(attribute)
        : null;
  }

  @SneakyThrows
  @Override
  public Set<AuthorityDto> convertToEntityAttribute(String dbData) {
    return dbData != null
        ? objectMapper.readValue(dbData, new TypeReference<>(){})
        : null;
  }
}
