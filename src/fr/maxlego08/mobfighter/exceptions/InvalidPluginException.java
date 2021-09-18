package fr.maxlego08.mobfighter.exceptions;

public class InvalidPluginException extends Exception {

	private static final long serialVersionUID = 5721389122281775896L;

	public InvalidPluginException(Throwable cause) {
		super(cause);
	}

	public InvalidPluginException() {
	}

	public InvalidPluginException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPluginException(String message) {
		super(message);
	}

}
