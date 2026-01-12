// (C) 2026 uchicom
package com.uchicom.h2m;

import com.uchicom.h2m.factory.di.DIFactory;
import java.util.logging.Logger;
import org.h2.tools.Server;

public class TcpServerMain extends AbstractMain {
  private static AbstractMain serverMain;

  public static void main(String[] args) {
    serverMain = DIFactory.tcpServerMain();
    serverMain.start(() -> Server.createTcpServer(args));
  }

  public TcpServerMain(Logger logger) {
    super(logger);
  }

  public static void shutdown() {
    if (serverMain == null) {
      return;
    }
    serverMain.stop();
  }
}
