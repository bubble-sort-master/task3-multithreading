package com.innowise.logisticsbase.state.impl;

import com.innowise.logisticsbase.entity.Truck;
import com.innowise.logisticsbase.state.TruckState;
import com.innowise.logisticsbase.util.LogColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StartDrivingState implements TruckState {

  private static final Logger logger = LogManager.getLogger();

  @Override
  public void handle(Truck truck) {
      logger.info(LogColor.GREEN + "Truck {} started driving (task={}, urgent={})" + LogColor.RESET,
              truck.getId(), truck.getTask(), truck.isUrgent());
      truck.setState(new TaskExecutionState());
  }
}
