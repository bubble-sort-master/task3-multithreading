package com.innowise.logisticsbase.main;

import com.innowise.logisticsbase.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
  public static void main(String[] args) {
    LogisticsBase base = LogisticsBase.getInstance();

    for (int i = 1; i <= 5; i++) {
      base.addTerminal(new Terminal(i));
    }

    ScheduledExecutorService train = Executors.newScheduledThreadPool(1);
    train.scheduleAtFixedRate(new Train(), 0, 2, TimeUnit.SECONDS);

    Random random = new Random();
    List<Thread> trucks = new ArrayList<>();

    for (int i = 1; i <= 20; i++) {
      int cargo = 10 + (i * 5);
      boolean isUrgent = random.nextBoolean();
      TruckTask task = random.nextBoolean() ? TruckTask.LOAD : TruckTask.UNLOAD;

      Thread truck = new Thread(new Truck(i, cargo, isUrgent, task));
      trucks.add(truck);
      truck.start();
    }

    for(Thread t: trucks){
      try {
        t.join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    train.shutdown();
  }
}
