package com.uchicom.h2m;

import java.sql.SQLException;

import org.h2.tools.Server;

public class TcpServerMain {
	private static Server tcpServer;
	private static boolean alive ;
	public static void main(String[] args) {
		try {
			tcpServer = Server.createTcpServer(args).start();
			System.out.println("tcp server start");
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
		if (tcpServer != null) {
			tcpServer.stop();
			System.out.println("tcp server stop");
		}
		alive = false;
	}

}
