package com.innowise.logisticsbase.entity;

import com.innowise.logisticsbase.state.TruckState;
import com.innowise.logisticsbase.state.impl.StartDrivingState;
import com.innowise.logisticsbase.util.LogColor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

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
    state.handle(this);
    LogisticsBase base = LogisticsBase.getInstance();
    Terminal terminal = base.acquireTerminal(urgent);

    executeTask(terminal);

    depart(base, terminal);
  }

  public boolean isUrgent() {
    return urgent;
  }

  public void executeTask(Terminal terminal){
    logger.info(LogColor.YELLOW + "Truck {} (urgent == {}) has taken terminal {}" + LogColor.RESET, id, urgent, terminal.id());
    state.handle(this);
  }

  public void depart(LogisticsBase base, Terminal terminal){
    state.handle(this);
    base.releaseTerminal(terminal);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Truck)) return false;
    Truck truck = (Truck) o;
    return id == truck.id &&
            cargoAmount == truck.cargoAmount &&
            urgent == truck.urgent &&
            Objects.equals(state, truck.state) &&
            task == truck.task;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cargoAmount, urgent, state, task);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Truck{");
    sb.append("id=").append(id)
            .append(", cargoAmount=").append(cargoAmount)
            .append(", urgent=").append(urgent)
            .append(", state=").append(state)
            .append(", task=").append(task)
            .append('}');
    return sb.toString();
  }

}
