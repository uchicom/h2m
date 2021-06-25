package com.uchicom.h2m;

import java.sql.SQLException;

import org.h2.tools.Server;

public class WebServerMain {
	private static Server webServer;
	private static boolean alive ;
	public static void main(String[] args) {
		try {
			webServer = Server.createWebServer(args).start();
			System.out.println("web server start");
			alive = true;
			while (alive) {
				Thread.sleep(1000);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void shutdown() {
		if (webServer != null) {
			webServer.stop();
			System.out.println("web server stop");
		}
		alive = false;
	}

}
