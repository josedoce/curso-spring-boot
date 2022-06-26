package github.josedoce.cursosb.resources.exception;

public class ValueExceededException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ValueExceededException(String msg) {
		super(msg);
	}
	
	public ValueExceededException(String msg, Throwable th) {
		super(msg, th);
	}
}
