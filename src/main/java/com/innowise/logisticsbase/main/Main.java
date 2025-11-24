package com.innowise.logisticsbase.main;

import com.innowise.logisticsbase.base.LogisticsBase;
import com.innowise.logisticsbase.terminal.Terminal;
import com.innowise.logisticsbase.truck.Truck;

import java.util.Random;

public class Main {
  public static void main(String[] args) {
    LogisticsBase base = LogisticsBase.getInstance();

    for (int i = 1; i <= 5; i++) {
      base.addTerminal(new Terminal(i));
    }

    Random random = new Random();

    for (int i = 1; i <= 20; i++) {
      int cargo = 10 + (i * 5);
      boolean isUrgent = random.nextBoolean();
      Thread truck = new Thread(new Truck(i, cargo, isUrgent));
      truck.start();
    }
  }
}
