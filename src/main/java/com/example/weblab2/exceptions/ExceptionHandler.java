package com.example.weblab2.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  public String handleException(Exception e, Model model) {
    model.addAttribute("errorMessage", e.getMessage());
    return "errorPage";
  }
}
