package com.example.weblab2.repositories;

import com.example.weblab2.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
  @Query("select u from User u where u.email = :email")
  Optional<User> findByEmail(String email);
}
