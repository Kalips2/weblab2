package com.example.weblab2.services;

import com.example.weblab2.data.LoginData;
import com.example.weblab2.data.RegisterData;

public interface AuthenticationService {
  boolean register(RegisterData data);
  String login(LoginData data);
}
