// (C) 2026 uchicom
package com.uchicom.h2m;

import com.uchicom.util.ThrowingSupplier;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.h2.tools.Server;

public abstract class AbstractMain {
  private final Logger logger;
  private Server webServer;
  private boolean alive;

  public AbstractMain(Logger logger) {
    this.logger = logger;
  }

  void start(ThrowingSupplier<Server, SQLException> supplier) {
    try {
      webServer = supplier.get().start();
      logger.info("server start");
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

  void stop() {
    if (webServer == null) {
      return;
    }
    webServer.stop();
    logger.info("server stop");
    alive = false;
  }
}
