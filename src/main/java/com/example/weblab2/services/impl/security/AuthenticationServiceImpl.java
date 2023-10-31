package com.example.weblab2.services.impl.security;

import static com.example.weblab2.exceptions.Exceptions.USER_NOT_FOUND;

import com.example.weblab2.data.LoginData;
import com.example.weblab2.data.RegisterData;
import com.example.weblab2.dto.security.AuthorityDto;
import com.example.weblab2.entities.User;
import com.example.weblab2.exceptions.InternalException;
import com.example.weblab2.repositories.UserRepository;
import com.example.weblab2.services.AuthenticationService;
import com.example.weblab2.services.JwtService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Class for Auth with JWT token.

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public boolean register(RegisterData data) {
    try {
      log.info("Try to register " + data.getUsername());
      Optional<User> byEmail = userRepository.findByEmail(data.getEmail());
      if (byEmail.isEmpty()) {
        User user = User
            .builder()
            .username(data.getUsername())
            .email(data.getEmail())
            .password(passwordEncoder.encode(data.getPassword()))
            .roles(Set.of(AuthorityDto.builder().role(data.getAuthorities()).build()))
            .enabled(true)
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .build();
        userRepository.saveAndFlush(user);
      } else {
        log.info("User with this email already exist!");
        return false;
      }
    } catch (Exception e) {
      log.info("Error occurred wile registering user");
      return false;
    }
    return true;
  }

  @Override
  public String login(LoginData data) {
    log.info("Try to log " + data.getEmail());
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword()));
    User user = userRepository.findByEmail(data.getEmail())
        .orElseThrow(() -> new InternalException(USER_NOT_FOUND));
    return jwtService.generateToken(user.getAuthorities(), user.getUsername());
  }
}
