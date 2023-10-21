package com.example.weblab2.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
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
public class Album {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private Date releaseDate;
  @Column(name = "photo_path")
  private String pathToPhoto;

  @ManyToOne
  @JoinColumn(name = "artist_id")
  private Artist artist;

  @ManyToOne
  @JoinColumn(name = "label_id")
  private Label label;

  @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
  private List<Song> songs = new ArrayList<>();
}
