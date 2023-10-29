package com.example.weblab2.controllers.rest;

import com.example.weblab2.data.LoginData;
import com.example.weblab2.data.RegisterData;
import com.example.weblab2.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secure")
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<Boolean> register(@RequestBody RegisterData data) {
    return ResponseEntity.ok(authenticationService.register(data));
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginData data) {
    return ResponseEntity.ok(authenticationService.login(data));
  }
}
