package edu.bu.metcs673.trackr.common;

/**
 * Contains all static strings to increase re-usability.
 * 
 * @author Tim Flucker
 *
 */
public class CommonConstants {

	// Allowed patterns in JWT filter (security related)
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String BEARER_PREFIX = "Bearer ";
	public static final String FAVICON_PATH = "/favicon.ico";
	public static final String IMAGES_PATH = "/images/.*";
	public static final String GENERATED_ASSETS_PATH = "/built/.*";
	public static final String ROOT_PATH = "/";
	public static final String REGISTER_PATH = "/register";
	public static final String LOGIN_PATH = "/login";
	public static final String LOGOUT_PATH = "/logout";

	// Cookie
	public static final String JWT_COOKIE_NAME = "jwtToken";
	public static final String USER_LOGGED_IN_COOKIE_NAME = "userLoggedIn";
	public static final String JWT_COOKIE_PATH = "/";
	public static final int JWT_COOKIE_MAX_AGE_MINUTES = 60 * 15; // 15 minutes

	// static strings used in APIs
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";

	public static final String NAME_REGEX ="^([A-Za-z-.,'0-9])+([ ])*$";
	public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	public static final String PASSWORD_REGEX = "^([A-Za-z-.,'0-9!?_])+$";

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

	// Transaction fields
	public static final String TRANSACTION_MONEY = "Money Type is a required field.";
	public static final String TRANSACTION_TIME = "Time is a required field.";
	public static final String TRANSACTION_COU = "Counterparty is a required field.";

	// length validation error messages - User
	public static final String INVALID_FIRST_NAME_LENGTH = "First Name must be less than 100 characters.";
	public static final String INVALID_LAST_NAME_LENGTH = "Last Name must be less than 100 characters.";
	public static final String INVALID_USERNAME_LENGTH = "Username must be less than 50 characters.";
	public static final String INVALID_PASSWORD_LENGTH = "Password must be less than 20 characters.";
	public static final String INVALID_EMAIL_LENGTH = "Email must be less than 100 characters.";

	// length validation error messages - BankAccount
	public static final String INVALID_ACCOUNT_DESC_LENGTH = "Account Description must be less than 255 characters.";

	// length validation error messages - Transaction
	public static final String INVALID_TRANSACTION_COU_LENGTH = "Counterparty must be less than 100 characters.";
	public static final String INVALID_TRANSACTION_TD_LENGTH = "Transaction Description must be less than 225 characters.";

	// custom validation error messages
	public static final String DUPLICATE_USERNAME = "Invalid USERNAME value provided. Please use another value.";
	public static final String DUPLICATE_EMAIL = "Invalid EMAIL value provided. Please use another value.";
	public static final String INVALID_EMAIL_FORMAT = "Email has an invalid format. Please try again.";
	public static final String INVALID_BALANCE_VALUE = "Balance must be a positive number or zero.";
	public static final String VALIDATION_ERRORS = "Invalid values detected in request body. Please review the errors and modify your request accordingly.";
	public static final String INVALID_BANK_ACCOUNT_ID = "Invalid bank account id. Please use another value.";
	public static final String INVALID_TRANSACTION_ID = "Invalid transaction id. Please use another value.";
	public static final String INVALID_USER_ID = "Invalid user id. Please use another value.";
	public static final String INVALID_CHARACTERS = "Invalid characters detected in {0}. Please use another value.";
	public static final String WRONG_PASSWORD = "Old password is wrong. Please try again.";

	// security-related error messages
	public static final String INVALID_CREDENTIALS = "Invalid Login Credentials.";
	public static final String UNAUTHORIZED_ACCESS = "You do not have access to the specified resource.";
	public static final String INVALID_TOKEN = "Invalid Token value.";

	// Success messages
	// USER MANAGEMENT
	public static final String CREATE_USER_SUCCESS = "Successfully created a new user! Please use the JWT token below to authenticate your API requests.";
	public static final String NEW_JWT_TOKEN = "Successfully created new JWT token!";
	public static final String GET_USER_PROFILE_SUCCESS = "Successfully loaded user profile!";
	public static final String UPDATE_USER_PROFILE_SUCCESS = "Successfully updated user profile!";

	// BANK ACCOUNT MANAGEMENT
	public static final String FIND_ALL_BANK_ACCOUNT = "Successfully found all bank account records associated to current user.";
	public static final String RETRIEVE_BANK_ACCOUNT = "Successfully retrieved bank account with id: {0}.";
	public static final String CREATE_BANK_ACCOUNT = "Successfully created a bank account with id: {0}.";
	public static final String MODIFY_BANK_ACCOUNT = "Successfully modified bank account with id: {0}.";
	public static final String DEACTIVATE_BANK_ACCOUNT = "Successfully deactivated bank account with id: {0}.";

	// TRANSACTION MANAGEMENT
	public static final String MODIFY_TRANSACTION = "Successfully modified the transaction with id: {0}.";
	public static final String CREATE_TRANSACTION = "Successfully created a transaction with id: {0}.";
	public static final String FIND_ALL_TRANSACTION = "Successfully found all transactions associated to bank account id: {0}.";
	public static final String FIND_ALL_TRANSACTION_FOR_USER = "Successfully found all transactions associated to user: {0}";
	public static final String RETRIEVE_TRANSACTION = "Successfully found the transaction with id: {0}.";
	public static final String INVALID_TRANSACTION = "Successfully invalidated the transaction with id: {0}.";

}
