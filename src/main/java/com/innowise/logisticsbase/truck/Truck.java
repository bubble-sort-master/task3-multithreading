package com.innowise.logisticsbase.truck;

import com.innowise.logisticsbase.base.LogisticsBase;
import com.innowise.logisticsbase.terminal.Terminal;
import com.innowise.logisticsbase.util.LogColor;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Truck implements Runnable {
  private static final Logger logger = LogManager.getLogger(Truck.class);

  private static final int UNITS_PER_SECOND = 2;
  private final int id;
  private final int cargoAmount;

  public Truck(int id, int cargoAmount) {
    this.id = id;
    this.cargoAmount = cargoAmount;
  }

  //TODO: would be rewritten using state pattern
  @Override
  public void run() {
    LogisticsBase base = LogisticsBase.getInstance();

    Terminal terminal = base.acquireFreeTerminal();
    logger.info(LogColor.GREEN + "Truck {} arrived and occupied terminal {}" + LogColor.RESET, id, terminal.id());

    int seconds = cargoAmount / UNITS_PER_SECOND;
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    base.addGoods(cargoAmount);
    logger.info(LogColor.YELLOW + "Truck {} unloaded {} goods. Current amount on base: {}" + LogColor.RESET, id, cargoAmount, base.getGoods());

    logger.info(LogColor.CYAN + "Truck {} departed from terminal {}" + LogColor.RESET, id, terminal.id());

    base.releaseTerminal(terminal);
  }
}
