package edu.bu.metcs673.trackr.user;

import org.springframework.stereotype.Component;

/**
 * Interface which defines methods which will be implemented in the
 * "UserServiceImpl". This interface is reusable, so other classes could extend
 * this if they wanted / needed to.
 * 
 * @author Tim Flucker
 *
 */
@Component
public interface TrackrUserService {

	/**
	 * Used to verify the association between the User and the Bank Account.
	 * 
	 * @param id
	 * @return User
	 */
	public TrackrUser findUserById(long id);

	/**
	 * Used by the JWT Filter to find a user by the provided username to
	 * authenticate the request.
	 * 
	 * @param username
	 * @return
	 */
	public TrackrUser findByUsername(String username);

	/**
	 * Creates a new record in the USERS table with the provided method parameters.
	 * These parameters are validated and if the validations fail, then no record is
	 * saved and an error is returned.
	 * 
	 * @param userInput
	 * @return String
	 */
	public String createUser(TrackrUserDTO userInput);

}
