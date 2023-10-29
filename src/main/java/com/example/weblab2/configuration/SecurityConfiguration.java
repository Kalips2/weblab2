package com.example.weblab2.configuration;

import com.example.weblab2.services.impl.security.GoogleOAuth2UserService;
import com.example.weblab2.services.impl.security.OAuth2FailureHandler;
import com.example.weblab2.services.impl.security.OAuth2SuccessHandler;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // for @PreAuthorize checking
@RequiredArgsConstructor
public class SecurityConfiguration {
  private final AuthEntryPoint authEntryPoint;
  private final OAuth2SuccessHandler successHandler;
  private final OAuth2FailureHandler failureHandler;
  private final UserDetailsService userDetailsService;

  // Dependencies needed for  default auth with JWT:
  private final AuthTokenFilter tokenFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  private final GoogleOAuth2UserService googleOAuth2UserService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors().disable()
        .csrf(CsrfConfigurer::disable)
        .authorizeHttpRequests(reg ->
            reg.requestMatchers(
                    "/albums/**",
                    "/rest/labels/**",
                    "/artists/**",
                    "/songs/**",
                    "/labels/**",
                    "/secure/**",
                    "/login/oauth2/**",
                    "/"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
        )
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2Login(oauth2 -> oauth2
            .authorizationEndpoint()
            .baseUri("/login/oauth2/authorize")
            .and()
            .redirectionEndpoint()
            .baseUri("/login/oauth2/callback/*")
            .and()
            .successHandler(successHandler)
            .failureHandler(failureHandler)
            .userInfoEndpoint(f -> f.userService(googleOAuth2UserService))
        )
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(e -> e.authenticationEntryPoint(authEntryPoint));
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000, http://localhost:8080"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(
        Arrays.asList("Content-Type", "content-type", "x-requested-with",
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Headers",
            "x-auth-token",
            "x-app-id", "Origin", "Accept",
            "X-Requested-With",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers",
            "Authorization"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}
