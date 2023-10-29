package com.example.weblab2.dto.security;

import com.example.weblab2.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


/*
 * Represents an authority granted to an Authentication object.
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDto implements GrantedAuthority {
  private Role role;

  @Override
  public String getAuthority() {
    return role.name();
  }
}
