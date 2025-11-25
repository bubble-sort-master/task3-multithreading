package com.innowise.logisticsbase.reader;

import com.innowise.logisticsbase.exception.MultiThreadException;

import java.util.List;

public interface ConfigurationReader {
    public void readConfiguration(String fileName) throws MultiThreadException;
}
