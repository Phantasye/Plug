package com.plug.world.content.transport;

public class GenericWarp extends Warp {
	
	private String locationName, message;
	private int x, y;
	
	public GenericWarp(String locationName, String message, int x, int y) {
		this.locationName = locationName;
		this.message = message;
		this.x = x;
		this.y = y;
	}

	@Override
	public String getLocationName() {
		return locationName;
	}

	@Override
	public String getWarpMessage() {
		return message;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
}
