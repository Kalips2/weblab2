package com.example.weblab2.dto.security;

import com.example.weblab2.entities.User;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements OAuth2User, UserDetails {
  private Long id;
  private String username;
  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getName() {
    return username;
  }

  public static OAuth2User generate(User user, Map<String, Object> attributes) {
    return UserDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .password(user.getPassword())
        .username(user.getUsername())
        .authorities(user.getAuthorities())
        .attributes(attributes)
        .build();
  }
}
