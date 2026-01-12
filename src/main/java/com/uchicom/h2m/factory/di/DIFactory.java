// (C) 2026 uchicom
package com.uchicom.h2m.factory.di;

import com.uchicom.h2m.PgServerMain;
import com.uchicom.h2m.TcpServerMain;
import com.uchicom.h2m.WebServerMain;
import com.uchicom.util.logging.DailyRollingFileHandler;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class DIFactory {

  public static WebServerMain webServerMain() {
    return new WebServerMain(logger());
  }

  public static TcpServerMain tcpServerMain() {
    return new TcpServerMain(logger());
  }

  public static PgServerMain pgServerMain() {
    return new PgServerMain(logger());
  }

  public static Logger logger() {

    try {
      var PROJECT_NAME = "h2m";
      var name =
          Stream.of(Thread.currentThread().getStackTrace())
              .map(StackTraceElement::getClassName)
              .filter(className -> className.endsWith("Main"))
              .findFirst()
              .orElse(PROJECT_NAME);
      Logger logger = Logger.getLogger(name);
      if (!PROJECT_NAME.equals(name)) {
        if (Arrays.stream(logger.getHandlers())
            .filter(handler -> handler instanceof DailyRollingFileHandler)
            .findFirst()
            .isEmpty()) {
          logger.addHandler(
              new DailyRollingFileHandler(
                  "./logs",
                  "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$s %2$s %5$s%6$s%n",
                  name + "_%d.log",
                  ZoneId.of("Asia/Tokyo")));
        }
      }
      return logger;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
