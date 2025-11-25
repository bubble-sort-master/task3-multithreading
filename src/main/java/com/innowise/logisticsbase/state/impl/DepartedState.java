package com.innowise.logisticsbase.state.impl;

import com.innowise.logisticsbase.entity.Truck;
import com.innowise.logisticsbase.state.TruckState;
import com.innowise.logisticsbase.util.LogColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DepartedState implements TruckState {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public void handle(Truck truck) {
    logger.info(LogColor.GREEN + "Truck {} has departed (urgent={})" + LogColor.RESET,
            truck.getId(), truck.isUrgent());
  }
}
