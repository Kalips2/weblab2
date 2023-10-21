package com.example.weblab2.dto;

import com.example.weblab2.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongDto {
  private Long id;
  private String title;
  private double duration;
  private Genre genre;
  private AlbumDto album;
}
