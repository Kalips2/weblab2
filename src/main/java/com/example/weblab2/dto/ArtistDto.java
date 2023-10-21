package com.example.weblab2.dto;

import com.example.weblab2.mappers.DataMapper;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtistDto {
  private Long id;
  private String name;
  private String surname;
  private Date dateOfBirth;

  public String dateOfBirthToString() {
    return DataMapper.stringFromDate(dateOfBirth);
  }
}
