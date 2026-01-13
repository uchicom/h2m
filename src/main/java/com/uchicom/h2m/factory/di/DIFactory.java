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
    return new WebServerMain(logger(), dailyRollingFileHandler());
  }

  public static TcpServerMain tcpServerMain() {
    return new TcpServerMain(logger(), dailyRollingFileHandler());
  }

  public static PgServerMain pgServerMain() {
    return new PgServerMain(logger(), dailyRollingFileHandler());
  }

  public static Logger logger() {

    var PROJECT_NAME = "h2m";
    var name = loggerName(PROJECT_NAME);
    Logger logger = Logger.getLogger(name);
    if (!PROJECT_NAME.equals(name)) {
      if (Arrays.stream(logger.getHandlers())
          .filter(handler -> handler instanceof DailyRollingFileHandler)
          .findFirst()
          .isEmpty()) {
        logger.addHandler(dailyRollingFileHandler());
      }
    }
    return logger;
  }

  static DailyRollingFileHandler dailyRollingFileHandler() {
    try {
      var PROJECT_NAME = "h2m";
      var name = loggerName(PROJECT_NAME);
      return new DailyRollingFileHandler(
          "./logs",
          "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$s %2$s %5$s%6$s%n",
          name + "_%d.log",
          ZoneId.of("Asia/Tokyo"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static String loggerName(String projectName) {
    return Stream.of(Thread.currentThread().getStackTrace())
        .map(StackTraceElement::getClassName)
        .filter(className -> className.endsWith("Main"))
        .findFirst()
        .orElse(projectName);
  }
}
