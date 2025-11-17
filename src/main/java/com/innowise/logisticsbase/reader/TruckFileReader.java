package com.innowise.logisticsbase.reader;

import java.util.List;

public interface TruckFileReader {
    List<String> readFile(String path);
}
