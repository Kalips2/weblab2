package com.example.weblab2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabelDto {
  private Long id;
  private String name;
  private CoordinateDto coordinates;

  public String coordinatesToString() {
    return coordinates == null
        ? ""
        : coordinates.getLat() + ", " + coordinates.getLon();
  }
}
