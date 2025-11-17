package com.innowise.logisticsbase.util;

import java.util.concurrent.atomic.AtomicLong;

public class TruckIdGenerator {
  private static final AtomicLong id = new AtomicLong(100);

  public long nextId() {
    return id.incrementAndGet();
  }
}
