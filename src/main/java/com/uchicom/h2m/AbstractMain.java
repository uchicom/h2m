// (C) 2026 uchicom
package com.uchicom.h2m;

import com.uchicom.util.ThrowingSupplier;
import com.uchicom.util.logging.DailyRollingFileHandler;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.h2.tools.Server;

public abstract class AbstractMain {
  private final Logger logger;
  private Server server;
  private boolean alive;
  private DailyRollingFileHandler handler;

  public AbstractMain(Logger logger, DailyRollingFileHandler handler) {
    this.logger = logger;
    this.handler = handler;
  }

  void start(ThrowingSupplier<Server, SQLException> supplier) {
    registerShutdownHook();
    try {
      server = supplier.get().start();
      logger.info(getClass().getName());
      alive = true;
      while (alive) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          logger.log(Level.SEVERE, "Sleep error", e);
        }
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "DB error", e);
    }
  }

  void registerShutdownHook() {
    if (isNotStandalone()) {
      return;
    }
    Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
  }

  boolean isNotStandalone() {
    return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
        .walk(
            frames -> {
              for (var f : (Iterable<StackWalker.StackFrame>) frames::iterator) {
                if (!f.getMethodName().equals("main")) {
                  continue;
                }
                return !AbstractMain.class.isAssignableFrom(f.getDeclaringClass());
              }
              return true;
            });
  }

  void stop() {
    if (server == null) {
      return;
    }
    server.stop();
    alive = false;
    try {
      handler.logInShutdownHook(Level.INFO, getClass().getName());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
