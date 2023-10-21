package com.example.weblab2.data;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AlbumData {
  @NotBlank
  private String title;
  @NotBlank
  private String releaseDate;
  @NotBlank
  private Long artistId;
  @NotBlank
  private Long labelId;
}
