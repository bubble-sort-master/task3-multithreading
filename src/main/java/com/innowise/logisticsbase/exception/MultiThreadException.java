package com.innowise.logisticsbase.exception;

public class MultiThreadException extends Exception{
  public MultiThreadException() {
  }

  public MultiThreadException(String message) {
    super(message);
  }

  public MultiThreadException(Throwable cause) {
    super(cause);
  }

  public MultiThreadException(String message, Throwable cause) {
    super(message, cause);
  }
}
