package com.innowise.logisticsbase.entity;

import com.innowise.logisticsbase.util.LogColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Train implements Runnable {

  private final double LOAD_FACTOR = 0.8;
  private final double EMPTY_FACTOR = 0.2;
  private final int BASE_CAPACITY = 150;
  private static final Logger logger = LogManager.getLogger();

  @Override
  public void run() {
    LogisticsBase base = LogisticsBase.getInstance();
    int cargo = base.getCargoAmount();

    if (cargo > BASE_CAPACITY * LOAD_FACTOR) {
      base.removeGoods(50);
      logger.info(LogColor.ORANGE + "Train removed cargo, current = {}" + LogColor.RESET, base.getCargoAmount());
    } else if (cargo < BASE_CAPACITY * EMPTY_FACTOR) {
      base.addGoods(50);
      logger.info(LogColor.ORANGE + "Train added cargo, current = {}" + LogColor.RESET, base.getCargoAmount());
    }
  }
  }


