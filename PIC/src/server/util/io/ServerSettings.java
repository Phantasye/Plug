package server.util.io;

import server.Test;
import server.util.Log;

public class ServerSettings implements Loadable {

	private String serverName;
	private int port;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Override
	public void initialize(Loadable data) {
		Test.setName(((ServerSettings) data).getServerName());
		Test.setPort(((ServerSettings) data).getPort());
		Log.printInfo("Server settings loaded!");
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
