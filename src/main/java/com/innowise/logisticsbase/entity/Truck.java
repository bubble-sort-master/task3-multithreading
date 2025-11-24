package com.innowise.logisticsbase.entity;

import com.innowise.logisticsbase.util.LogColor;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Truck implements Runnable {
  private static final Logger logger = LogManager.getLogger(Truck.class);

  private static final int UNITS_PER_SECOND = 5;
  private final int id;
  private final int cargoAmount;
  private final boolean isUrgent;

  public Truck(int id, int cargoAmount, boolean isUrgent) {
    this.id = id;
    this.cargoAmount = cargoAmount;
    this.isUrgent = isUrgent;
  }

  @Override
  public void run() {
    LogisticsBase base = LogisticsBase.getInstance();
    logger.info("Truck {} started (urgent={})", id, isUrgent);

    Terminal terminal = base.acquireTerminal(isUrgent);
    logger.info(LogColor.GREEN + "Truck {} (urgent={}) arrived and occupied terminal {}" + LogColor.RESET,
            id, isUrgent, terminal.id());

    int seconds = cargoAmount / UNITS_PER_SECOND;
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    base.addGoods(cargoAmount);
    logger.info(LogColor.YELLOW + "Truck {} unloaded {} goods. Current amount on base: {}" + LogColor.RESET,
            id, cargoAmount, base.getGoods());

    logger.info(LogColor.CYAN + "Truck {} departed from terminal {}" + LogColor.RESET, id, terminal.id());

    base.releaseTerminal(terminal);
  }
}
