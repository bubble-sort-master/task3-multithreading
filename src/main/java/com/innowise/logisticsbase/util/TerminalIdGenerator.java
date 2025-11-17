package com.innowise.logisticsbase.util;

public class TerminalIdGenerator {
  private static long id = 0;

  public long nextId() {
    return ++id;
  }
}
