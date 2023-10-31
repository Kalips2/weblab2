package com.example.weblab2.security.shared;

import com.example.weblab2.configuration.AuthEntryPoint;
import com.example.weblab2.configuration.AuthTokenFilter;
import com.example.weblab2.services.JwtService;
import com.example.weblab2.services.impl.security.GoogleOAuth2UserService;
import com.example.weblab2.services.impl.security.OAuth2FailureHandler;
import com.example.weblab2.services.impl.security.OAuth2SuccessHandler;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

public abstract class SecurityBaseMocker {

  @MockBean
  private JwtService jwtService;
  @MockBean
  private AuthEntryPoint authEntryPoint;
  @MockBean
  private OAuth2SuccessHandler successHandler;
  @MockBean
  private OAuth2FailureHandler failureHandler;
  @MockBean
  private UserDetailsService userDetailsService;
  @MockBean
  private AuthTokenFilter tokenFilter;
  @MockBean
  private GoogleOAuth2UserService googleOAuth2UserService;
}
