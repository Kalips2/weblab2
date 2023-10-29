package com.example.weblab2.services.impl.security;

import com.example.weblab2.dto.security.AuthorityDto;
import com.example.weblab2.dto.security.UserDto;
import com.example.weblab2.enums.Role;
import com.example.weblab2.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtService jwtService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException {
    String targetUrl = determineTargetUrl(request, response, authentication);
    if (response.isCommitted()) return;
    clearAuthenticationAttributes(request);
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }

  @Override
  protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    // todo: fix a bug with authorities.
    String token = jwtService.generateToken(Set.of(AuthorityDto.builder().role(Role.ROLE_USER).build()), authentication.getName());
    log.info("Google jwt token = " + token);
    // or another way to pass token
    return UriComponentsBuilder
        .fromUriString("/albums")
        .queryParam("token", token)
        .build()
        .toUriString();
  }
}
