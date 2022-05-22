package edu.bu.metcs673.trackr.common;

import org.apache.commons.lang3.StringUtils;

/**
 * Contains all static strings to increase re-usability.
 * 
 * @author Tim Flucker
 *
 */
public class CommonConstants {

	// static strings used in APIs
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";

	public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

	// ------------- Error messages -------------
	// Null / Blank validation error messages
	public static final String BLANK_FIRST_NAME = "First Name is a required field.";
	public static final String BLANK_LAST_NAME = "Last Name is a required field.";
	public static final String BLANK_USERNAME = "Username is a required field.";
	public static final String BLANK_PASSWORD = "Password is a required field.";
	public static final String BLANK_EMAIL = "Email is a required field.";

	// length validation error messages
	public static final String INVALID_FIRST_NAME_LENGTH = "First Name must be less than 100 characters.";
	public static final String INVALID_LAST_NAME_LENGTH = "Last Name must be less than 100 characters.";
	public static final String INVALID_USERNAME_LENGTH = "Username must be less than 50 characters.";
	public static final String INVALID_PASSWORD_LENGTH = "Password must be less than 20 characters.";
	public static final String INVALID_EMAIL_LENGTH = "Password must be less than 100 characters.";

	// custom validation error messages
	public static final String DUPLICATE_USERNAME = "Invalid USERNAME value. Please use another value.";
	public static final String INVALID_EMAIL_FORMAT = "Email has an invalid format. Please try again.";
	
	// Success messages
	public static final String CREATE_USER_SUCCESS = "Successfully created a new user!  Token value: ";
	public static final String NEW_JWT_TOKEN = "New JWT Token: ";
	
	
	public static final String INVALID_CREDENTIALS = "Invalid Login Credentials ...";
	
	// enum used for Account Status
	public static enum ACCOUNT_STATUS {
		ACTIVE, INACTIVE;

		public boolean contains(String status) {
			for (ACCOUNT_STATUS acctStatus : ACCOUNT_STATUS.values()) {
				if (StringUtils.equals(acctStatus.toString(), status)) {
					return true;
				}
			}
			return false;
		}
	};
}
