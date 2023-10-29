package com.example.weblab2.dto.map;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinateDto {
  private double lat;
  private double lon;

  public CoordinateDto(String coordinateString) {
    Pattern pattern = Pattern.compile("\\[?(-?\\d+\\.\\d+), (-?\\d+\\.\\d+)]?");
    Matcher matcher = pattern.matcher(coordinateString);

    if (matcher.matches()) {
      this.lat = Double.parseDouble(matcher.group(1));
      this.lon = Double.parseDouble(matcher.group(2));
    } else {
      throw new IllegalArgumentException("Invalid coordinate format for " + coordinateString);
    }
  }
}
