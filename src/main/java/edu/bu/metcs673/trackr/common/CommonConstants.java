package edu.bu.metcs673.trackr.common;

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
	// TrackrUser fields
	public static final String BLANK_FIRST_NAME = "First Name is a required field.";
	public static final String BLANK_LAST_NAME = "Last Name is a required field.";
	public static final String BLANK_USERNAME = "Username is a required field.";
	public static final String BLANK_PASSWORD = "Password is a required field.";
	public static final String BLANK_EMAIL = "Email is a required field.";

	// Bank Account fields
	public static final String BLANK_ACCOUNT_TYPE = "Account Type is a required field.";
	public static final String BLANK_BALANCE = "Balance is a required field.";

	// length validation error messages - User
	public static final String INVALID_FIRST_NAME_LENGTH = "First Name must be less than 100 characters.";
	public static final String INVALID_LAST_NAME_LENGTH = "Last Name must be less than 100 characters.";
	public static final String INVALID_USERNAME_LENGTH = "Username must be less than 50 characters.";
	public static final String INVALID_PASSWORD_LENGTH = "Password must be less than 20 characters.";
	public static final String INVALID_EMAIL_LENGTH = "Email must be less than 100 characters.";

	// length validation error messages - BankAccount
	public static final String INVALID_ACCOUNT_DESC_LENGTH = "Account Description must be less than 255 characters.";

	// custom validation error messages
	public static final String DUPLICATE_USERNAME = "Invalid USERNAME value. Please use another value.";
	public static final String INVALID_EMAIL_FORMAT = "Email has an invalid format. Please try again.";
	public static final String INVALID_BALANCE_VALUE = "Balance must be a positive number or zero.";
	public static final String VALIDATION_ERRORS = "Invalid values detected in request body. Please review the errors and modify your request accordingly.";

	// security-related messages
	public static final String INVALID_CREDENTIALS = "Invalid Login Credentials";
	public static final String UNAUTHORIZED_ACCESS = "You do not have access to the specified resource.";
	public static final String INVALID_TOKEN = "Invalid Token value.";

	
	// Success messages
	public static final String CREATE_USER_SUCCESS = "Successfully created a new user! Please use the JWT token below to authenticate your API requests.";
	public static final String NEW_JWT_TOKEN = "Successfully created new JWT token!";


	public static final String DEACTIVATE_BANK_ACCOUNT = "Successfully deactivated Bank Account with id: {0}";

}
