package com.innowise.logisticsbase.base;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.innowise.logisticsbase.terminal.Terminal;

public class LogisticsBase {
  private final AtomicInteger goods = new AtomicInteger(0);
  private final Deque<Terminal> freeTerminals = new ArrayDeque<>();
  private final Lock lock = new ReentrantLock();
  private final Deque<Condition> waitingQueue = new ArrayDeque<>();

  private LogisticsBase() {}

  private static class Holder {
    private static final LogisticsBase instance = new LogisticsBase();
  }

  public static LogisticsBase getInstance() {
    return Holder.instance;
  }

  public void addTerminal(Terminal terminal) {
    freeTerminals.addLast(terminal);
  }

  public Terminal acquireTerminal(boolean isUrgent) {
    lock.lock();
    try {
      if (!freeTerminals.isEmpty()) {
        return freeTerminals.pollFirst();
      }

      Condition condition = lock.newCondition();
      if (isUrgent) {
        waitingQueue.addFirst(condition);
      } else {
        waitingQueue.addLast(condition);
      }

      try {
        condition.await();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }

      return freeTerminals.pollFirst();
    } finally {
      lock.unlock();
    }
  }

  public void releaseTerminal(Terminal terminal) {
    lock.lock();
    try {
      freeTerminals.addLast(terminal);
      Condition next = waitingQueue.pollFirst();
      if (next != null) {
        next.signal();
      }
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
