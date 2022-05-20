package edu.bu.metcs673.trackr.api;

import java.util.Date;

import edu.bu.metcs673.trackr.common.CommonConstants;
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
@AllArgsConstructor
public class GenericApiResponse {

	private String status;
	private Date date;
	private String message;

	public static GenericApiResponse successResponse(String message) {
		return new GenericApiResponse(CommonConstants.SUCCESS, new Date(), message);
	}

	public static GenericApiResponse errorResponse(String message) {
		return new GenericApiResponse(CommonConstants.ERROR, new Date(), message);
	}
}
