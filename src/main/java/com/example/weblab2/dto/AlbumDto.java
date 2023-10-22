package com.example.weblab2.dto;

import com.example.weblab2.mappers.DataMapper;
import java.util.Base64;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumDto {
  private Long id;
  private String title;
  private Date releaseDate;
  private ArtistDto artist;
  private LabelDto label;
  private byte[] photo;

  public String releaseDateToString() {
    return DataMapper.stringFromDate(releaseDate);
  }

  public String generateImage() {
    return Base64.getEncoder().encodeToString(getPhoto());
  }
}
