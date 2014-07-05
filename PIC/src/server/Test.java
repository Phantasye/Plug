package server;


public class Test {
	
	private static String serverName;
	private static int port;

	public static String getServerName() {
		return serverName;
	}

	public static void setName(String name) {
		Test.serverName = name;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		Test.port = port;
	}
}
