package com.innowise.logisticsbase.main;

import com.innowise.logisticsbase.entity.LogisticsBase;
import com.innowise.logisticsbase.entity.Terminal;
import com.innowise.logisticsbase.entity.Truck;
import com.innowise.logisticsbase.entity.TruckTask;

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
      TruckTask task = random.nextBoolean() ? TruckTask.LOAD : TruckTask.UNLOAD;

      Thread truckThread = new Thread(new Truck(i, cargo, isUrgent, task));
      truckThread.start();
    }
  }
}
