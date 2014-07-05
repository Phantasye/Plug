package server.util.exceptions;

public class InsufficientFundsException extends Exception {

	private static final long serialVersionUID = -2028385493038573791L;

	public InsufficientFundsException() {
		super();
	}

	@Override
	public String getMessage() {
		return "You do not have enough funds.";
	}
	
	@Override
	public Throwable getCause() {
		return this;
	}

}
