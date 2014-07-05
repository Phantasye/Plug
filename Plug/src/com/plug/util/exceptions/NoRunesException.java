package com.plug.util.exceptions;

public class NoRunesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3356406093879443039L;
	
	public NoRunesException() {
		super();
	}

	@Override
	public String getMessage() {
		return "You do not have the required runes to do this.";
	}
	
	@Override
	public Throwable getCause() {
		return this;
	}

}
