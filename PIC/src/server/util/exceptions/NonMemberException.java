package server.util.exceptions;

public class NonMemberException extends Exception {

	private static final long serialVersionUID = 6255350674169990975L;

	@Override
	public String getMessage() {
		return "You must be a member to do this.";
	}
	
	@Override
	public Throwable getCause() {
		return this;
	}
	
}
