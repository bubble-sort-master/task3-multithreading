package com.innowise.logisticsbase.entity;

import com.innowise.logisticsbase.state.TruckState;
import com.innowise.logisticsbase.state.impl.StartDrivingState;
import com.innowise.logisticsbase.util.LogColor;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Truck implements Runnable {
  private static final Logger logger = LogManager.getLogger(Truck.class);

  private final int id;
  private final int cargoAmount;
  private final boolean urgent;
  private TruckState state;
  private TruckTask task;

  public Truck(int id, int cargoAmount, boolean urgent, TruckTask task) {
    this.id = id;
    this.cargoAmount = cargoAmount;
    this.urgent = urgent;
    this.state = new StartDrivingState();
    this.task = task;
  }

  public int getId() {
    return id;
  }

  public void setState(TruckState state) {
    this.state = state;
  }

  public TruckTask getTask() {
    return task;
  }

  public int getCargoAmount() {
    return cargoAmount;
  }

  public TruckState getState() {
    return state;
  }

  @Override
  public void run() {
    state.handle(this);//started
    LogisticsBase base = LogisticsBase.getInstance();
    Terminal terminal = base.acquireTerminal(urgent);

    state.handle(this);//executing task

    state.handle(this);//departed
    base.releaseTerminal(terminal);
  }

  public boolean isUrgent() {
    return urgent;
  }

}
