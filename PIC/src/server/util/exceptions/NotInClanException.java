package server.util.exceptions;

public class NotInClanException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8647529285344737862L;
	
	@Override
	public String getMessage() {
		return "You're not in a clan.";
	}
	
	@Override
	public Throwable getCause() {
		return this;
	}

}
