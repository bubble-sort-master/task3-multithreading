package com.innowise.logisticsbase.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.innowise.logisticsbase.terminal.Terminal;

public class LogisticsBase {
  private final AtomicInteger goods = new AtomicInteger(0);
  private final List<Terminal> terminals = new ArrayList<>();
  private final Lock lock = new ReentrantLock();
  private final Condition terminalAvailable = lock.newCondition();
  private final Set<Terminal> busyTerminals = new HashSet<>();

  private LogisticsBase() {}

  private static class Holder {
    private static final LogisticsBase instance = new LogisticsBase();
  }

  public static LogisticsBase getInstance() {
    return Holder.instance;
  }

  public void addTerminal(Terminal terminal) {
    terminals.add(terminal);
  }

  //TODO: change try/catch, exception handling?
  public Terminal acquireFreeTerminal() {
    lock.lock();
    try {
      while (true) {
        for (Terminal terminal : terminals) {
          if (!busyTerminals.contains(terminal)) {
            busyTerminals.add(terminal);
            return terminal;
          }
        }
        try{
          terminalAvailable.await();
        }catch (InterruptedException e){
          Thread.currentThread().interrupt();
        }
      }
    } finally {
      lock.unlock();
    }
  }

  public void releaseTerminal(Terminal terminal) {
    lock.lock();
    try {
      busyTerminals.remove(terminal);
      terminalAvailable.signalAll();
    } finally {
      lock.unlock();
    }
  }

  public void addGoods(int amount) {
    goods.addAndGet(amount);
  }

  public int getGoods() {
    return goods.get();
  }
}
