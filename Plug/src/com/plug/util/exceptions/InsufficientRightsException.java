package com.plug.util.exceptions;

public class InsufficientRightsException extends Exception {
	
	private static final long serialVersionUID = -6591460307741853234L;

	public InsufficientRightsException() {
		super();
	}

	@Override
	public String getMessage() {
		return "You do not have permission to do this.";
	}
	
	@Override
	public Throwable getCause() {
		return this;
	}


}
