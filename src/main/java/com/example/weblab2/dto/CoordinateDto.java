package com.example.weblab2.dto;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinateDto {
  private final Pattern pattern = Pattern.compile("\\[?(-?\\d+\\.\\d+), (-?\\d+\\.\\d+)]?");

  private double lat;
  private double lon;

  public CoordinateDto(String coordinateString) {
    Matcher matcher = pattern.matcher(coordinateString);

    if (matcher.matches()) {
      this.lat = Double.parseDouble(matcher.group(1));
      this.lon = Double.parseDouble(matcher.group(2));
    } else {
      throw new IllegalArgumentException("Invalid coordinate format for " + coordinateString);
    }
  }
}
