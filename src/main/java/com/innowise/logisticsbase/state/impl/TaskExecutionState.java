package com.innowise.logisticsbase.state.impl;

import com.innowise.logisticsbase.entity.LogisticsBase;
import com.innowise.logisticsbase.entity.Truck;
import com.innowise.logisticsbase.state.TruckState;
import com.innowise.logisticsbase.util.LogColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class TaskExecutionState implements TruckState {
  private static final Logger logger = LogManager.getLogger();

  private static final int UNITS_PER_SECOND = 5;

  @Override
  public void handle(Truck truck) {
    LogisticsBase base = LogisticsBase.getInstance();

    int id = truck.getId();
    int cargoAmount = truck.getCargoAmount();
    boolean urgent = truck.isUrgent();

    int workingTime = truck.getCargoAmount() / UNITS_PER_SECOND;
    try {
      switch (truck.getTask()) {
        case LOAD :
          logger.info(LogColor.YELLOW + "Truck {} (urgent == {}) is loading {} units of cargo..." + LogColor.RESET, id, urgent, cargoAmount);
          base.addGoods(cargoAmount);
          break;
        case UNLOAD: logger.info(LogColor.YELLOW + "Truck {} (urgent == {}) is unloading {} units of cargo..." + LogColor.RESET, id, urgent, cargoAmount);
          base.removeGoods(cargoAmount);
          break;
        default: logger.warn("truck behavior not found");
      }
      TimeUnit.SECONDS.sleep(workingTime);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    truck.setState(new DepartedState());
  }
}
