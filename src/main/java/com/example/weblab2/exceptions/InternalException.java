package com.example.weblab2.exceptions;

public class InternalException extends RuntimeException {
  public InternalException(Exceptions exception) {
    super(exception.getMessage());
  }
}
