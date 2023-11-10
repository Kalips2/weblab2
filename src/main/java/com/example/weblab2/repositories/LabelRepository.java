package com.example.weblab2.repositories;

import com.example.weblab2.entities.Label;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LabelRepository extends JpaRepository<Label, Long> {
  @Query("select l from Label l where l.name = :name")
  Optional<Label> findByName(String name);
}