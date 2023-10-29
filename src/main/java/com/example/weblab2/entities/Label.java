package com.example.weblab2.entities;

import com.example.weblab2.converters.CoordinatesConverter;
import com.example.weblab2.dto.map.CoordinateDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Label {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @Convert(converter = CoordinatesConverter.class)
  private CoordinateDto coordinates;

  @OneToMany(mappedBy = "label", cascade = CascadeType.ALL)
  private List<Album> albums = new ArrayList<>();
}
