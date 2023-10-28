package com.example.weblab2.data;

import com.example.weblab2.dto.CoordinateDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LabelData {
  @NotBlank
  private String name;
  private CoordinateDto coordinates;

  public LabelData(String name, String coordinates) {
    this.name = name;
    this.coordinates = new CoordinateDto(coordinates);
  }
}
