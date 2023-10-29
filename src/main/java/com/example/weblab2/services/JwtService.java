package com.example.weblab2.services;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  String getTokenFromRequest(HttpServletRequest request);

  boolean validateJwtToken(String token);

  String getEmailFromToken(String token);

  String generateToken(Collection<? extends GrantedAuthority> roles, String username);
}
