package com.example.weblab2.data;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtistData {
  @NotBlank
  private String name;
  @NotBlank
  private String surname;
  @NotBlank
  private String dateOfBirth;
}
