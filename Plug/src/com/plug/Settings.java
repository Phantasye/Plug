package com.plug;


public class Settings {
	
	private static String serverName;
	private static int port;

	public static String getServerName() {
		return serverName;
	}

	public static void setName(String name) {
		Settings.serverName = name;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		Settings.port = port;
	}
}
