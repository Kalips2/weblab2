package com.example.weblab2.repositories;

import com.example.weblab2.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}