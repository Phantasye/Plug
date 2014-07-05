package com.plug.util.io;

import com.plug.Settings;
import com.plug.util.Log;

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
		Settings.setName(((ServerSettings) data).getServerName());
		Settings.setPort(((ServerSettings) data).getPort());
		Log.printInfo("Server settings loaded!");
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
