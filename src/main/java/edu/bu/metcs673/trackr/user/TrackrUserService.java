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
	 * @param id User ID
	 * @return User
	 */
	public TrackrUser findUserById(long id);

	/**
	 * Get the current user form DB after getting the username from the JWT.
	 *
	 * @return Current user
	 */
	public TrackrUser getCurrentUser();

	/**
	 * Get current user profile with only mutable fields in the profile. Change password is a separate flow.
	 *
	 * @return User DTO
	 */
	public TrackrUserDTO getCurrentUserProfile();

	/**
	 * Creates a new record in the USERS table with the provided method parameters.
	 * These parameters are validated and if the validations fail, then no record is
	 * saved and an error is returned.
	 * 
	 * @param userInput User DTO
	 * @return String
	 */
	public String createUser(TrackrUserDTO userInput);

	/**
	 * Update user information but does not handle change password. We will have a separate endpoint for that.
	 *
	 * @param dto User DTO
	 * @return JWT
	 */
	public TrackrUserDTO updateUser(TrackrUserDTO dto);

}
