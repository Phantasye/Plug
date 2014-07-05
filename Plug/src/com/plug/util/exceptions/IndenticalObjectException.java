package com.plug.util.exceptions;

public class IndenticalObjectException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IndenticalObjectException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "That has no effect.";
	}
	
	@Override
	public Throwable getCause() {
		return this;
	}

}
