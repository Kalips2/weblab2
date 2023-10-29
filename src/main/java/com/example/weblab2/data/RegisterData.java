package com.example.weblab2.data;

import com.example.weblab2.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterData {
  private String email;
  private String password;
  private String username;
  private Role authorities;
}
