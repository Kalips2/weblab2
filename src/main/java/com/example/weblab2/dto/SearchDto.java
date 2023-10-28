package com.example.weblab2.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchDto {
  private int page = 0;
  private int size = 5;
  private int skip = 0;

  public static SearchDto processSearchDTO(SearchDto searchDTO) {
    SearchDto defaultSearchDTO = new SearchDto();
    if (searchDTO != null) {
      defaultSearchDTO.setPage(searchDTO.getPage());
      defaultSearchDTO.setSize(searchDTO.getSize());
      defaultSearchDTO.setSkip(searchDTO.getSkip());
    }
    return defaultSearchDTO;
  }
}
