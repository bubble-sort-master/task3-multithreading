package com.innowise.logisticsbase.parser;

import com.innowise.logisticsbase.entity.Truck;

import java.util.List;

public interface TruckParser {
  List<Truck> parseFromList(List<String> data);
}
