package edu.bu.metcs673.trackr.api;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import edu.bu.metcs673.trackr.common.CommonConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Simple object used to contain success and error responses to return
 * meaningful information back to the user.
 * 
 * @author Tim Flucker
 *
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GenericApiResponse {

	@NotNull
	private String status;
	@NotNull
	private LocalDate date;
	@NotNull
	private String message;

	/**
	 * Factory method to create a successful response with the provided message.
	 * 
	 * @param message
	 * @return
	 */
	public static GenericApiResponse successResponse(String message) {
		return new GenericApiResponse(CommonConstants.SUCCESS, LocalDate.now(), message);
	}

	/**
	 * Factory method to create an error response with the provided message.
	 * 
	 * @param message
	 * @return
	 */
	public static GenericApiResponse errorResponse(String message) {
		return new GenericApiResponse(CommonConstants.ERROR, LocalDate.now(), message);
	}
}
