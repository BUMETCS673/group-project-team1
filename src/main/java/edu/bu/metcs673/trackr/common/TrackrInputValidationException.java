package edu.bu.metcs673.trackr.common;

/**
 * Custom Exception that is used for API validation errors.
 * 
 * @author Tim Flucker
 *
 */
public class TrackrInputValidationException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	private String message;

	public String getMessage() {
		return message;
	}

	/**
	 * Constructor to create new exception with custom message.
	 * 
	 * @param message
	 */
	public TrackrInputValidationException(String message) {
		this.message = message;
	}

	/**
	 * Constructor to create a new custom exception with the provided Exception's
	 * message.
	 * 
	 * @param e
	 */
	public TrackrInputValidationException(Exception e) {
		this.message = e.getMessage();
	}

}
