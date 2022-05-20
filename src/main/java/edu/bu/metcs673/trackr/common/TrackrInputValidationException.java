package edu.bu.metcs673.trackr.common;

public class TrackrInputValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public String getMessage() {
		return message;
	}

	public TrackrInputValidationException(String message) {
		this.message = message;
	}

	public TrackrInputValidationException(Exception e) {
		this.message = e.getMessage();
	}

}
