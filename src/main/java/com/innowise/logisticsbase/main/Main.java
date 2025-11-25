package com.innowise.logisticsbase.main;

import com.innowise.logisticsbase.entity.*;
import com.innowise.logisticsbase.exception.MultiThreadException;
import com.innowise.logisticsbase.reader.impl.ConfigurationReaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
  private static final Logger logger = LogManager.getLogger();

  public static void main(String[] args) {
    ConfigurationReaderImpl config = new ConfigurationReaderImpl();
    try {
      config.readConfiguration("data/config.txt");
    } catch (MultiThreadException e) {
      logger.error("Failed to read configuration file: {}", e.getMessage(), e);
      return;
    }

    LogisticsBase base = LogisticsBase.getInstance();

    for (int i = 1; i <= config.getTerminalsCount(); i++) {
      base.addTerminal(new Terminal(i));
    }

    ScheduledExecutorService train = Executors.newScheduledThreadPool(1);
    train.scheduleAtFixedRate(new Train(), 0, 2, TimeUnit.SECONDS);

    Random random = new Random();
    List<Thread> trucks = new ArrayList<>();

    for (int i = 1; i <= config.getTrucksCount(); i++) {
      int cargo = config.getCargoMin() + random.nextInt(config.getCargoMax() - config.getCargoMin() + 1);
      boolean isUrgent = random.nextBoolean();
      TruckTask task = random.nextBoolean() ? TruckTask.LOAD : TruckTask.UNLOAD;

      Thread truck = new Thread(new Truck(i, cargo, isUrgent, task));
      trucks.add(truck);
      truck.start();
    }

    for (Thread t : trucks) {
      try {
        t.join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    train.shutdown();
  }
}
