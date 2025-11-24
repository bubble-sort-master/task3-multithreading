package com.innowise.logisticsbase.state;

import com.innowise.logisticsbase.entity.Truck;

public interface TruckState {
  void handle(Truck truck);
}
