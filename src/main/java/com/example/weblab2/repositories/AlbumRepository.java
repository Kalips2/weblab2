package com.example.weblab2.repositories;

import com.example.weblab2.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlbumRepository extends JpaRepository<Album, Long> {

  @Query("SELECT COUNT(a) FROM Album a WHERE a.label.id = :labelId")
  Long countAlbumsByLabelId(@Param("labelId") Long labelId);
}