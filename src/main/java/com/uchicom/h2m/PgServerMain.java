package com.uchicom.h2m;

import java.sql.SQLException;

import org.h2.tools.Server;

public class PgServerMain {
	private static Server server;
	private static boolean alive ;
	public static void main(String[] args) {
		try {
			server = Server.createPgServer(args).start();
			System.out.println("pg server start");
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
		if (server != null) {
			server.stop();
			System.out.println("pg server stop");
		}
		alive = false;
	}
}
