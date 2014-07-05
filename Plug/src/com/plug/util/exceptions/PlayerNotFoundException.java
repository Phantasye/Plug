package com.plug.util.exceptions;

public class PlayerNotFoundException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6844394376761191254L;

	public PlayerNotFoundException() {
		super();
	}

	@Override
	public String getMessage() {
		return "Player not found.";
	}
	
	@Override
	public Throwable getCause() {
		return this;
	}
	
}
