package com.example.weblab2.mappers;

import com.example.weblab2.dto.security.AuthorityDto;
import com.example.weblab2.entities.User;
import com.example.weblab2.enums.Role;
import java.util.Set;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
  public User dtoToEntity(String email, String username) {
    return User.builder()
        .email(email)
        .username(username)
        .accountNonExpired(true)
        .enabled(true)
        .accountNonLocked(true)
        .credentialsNonExpired(true)
        .credentialsNonExpired(true)
        .roles(Set.of( new AuthorityDto(Role.ROLE_USER)))
        .build();
  }
}
