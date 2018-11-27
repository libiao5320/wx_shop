package cc.royao.commons.exception;

public class NoFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoFoundException() {
	}

	public NoFoundException(String message) {
		super(message);
	}

	public NoFoundException(Throwable cause) {
		super(cause);
	}

	public NoFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
