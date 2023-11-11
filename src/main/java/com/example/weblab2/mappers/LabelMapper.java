package com.example.weblab2.mappers;

import com.example.weblab2.data.LabelData;
import com.example.weblab2.dto.LabelDto;
import com.example.weblab2.elastic.dto.ArtistElasticDto;
import com.example.weblab2.elastic.dto.LabelElasticDto;
import com.example.weblab2.entities.Artist;
import com.example.weblab2.entities.Label;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LabelMapper {
  public LabelDto entityToDto(Label label) {
    return LabelDto.builder()
        .id(label.getId())
        .name(label.getName())
        .coordinates(label.getCoordinates())
        .build();
  }

  public Label dataToEntity(LabelData label) {
    return Label.builder()
        .name(label.getName())
        .coordinates(label.getCoordinates())
        .build();
  }

  public LabelElasticDto entityToElasticDto(LabelData label) {
    return LabelElasticDto.builder()
        .name(label.getName())
        .coordinates(label.getCoordinates().toString())
        .build();
  }
}
