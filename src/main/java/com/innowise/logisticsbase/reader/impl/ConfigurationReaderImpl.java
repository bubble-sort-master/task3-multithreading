package com.innowise.logisticsbase.reader.impl;

import com.innowise.logisticsbase.exception.MultiThreadException;
import com.innowise.logisticsbase.reader.ConfigurationReader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ConfigurationReaderImpl implements ConfigurationReader {
  private int trucksCount;
  private int terminalsCount;
  private int cargoMin;
  private int cargoMax;
  private final String DELIMITER = "=";

  public void readConfiguration(String fileName) throws MultiThreadException {
    try {
      List<String> lines = Files.readAllLines(Paths.get(fileName));
      for (String line : lines) {
        if (line.trim().isEmpty()) {
          continue;
        }
        String[] parts = line.split(DELIMITER);
        if (parts.length == 2) {
          String key = parts[0].trim();
          String value = parts[1].trim();
          switch (key) {
            case "trucks.count" -> trucksCount = Integer.parseInt(value);
            case "terminals.count" -> terminalsCount = Integer.parseInt(value);
            case "cargo.min" -> cargoMin = Integer.parseInt(value);
            case "cargo.max" -> cargoMax = Integer.parseInt(value);
          }
        }
      }
    } catch (Exception e) {
      throw new MultiThreadException("Failed to read config file", e);
    }
  }

  public int getTrucksCount() { return trucksCount; }
  public int getTerminalsCount() { return terminalsCount; }
  public int getCargoMin() { return cargoMin; }
  public int getCargoMax() { return cargoMax; }
}
