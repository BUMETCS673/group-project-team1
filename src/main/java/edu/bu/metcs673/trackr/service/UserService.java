package edu.bu.metcs673.trackr.service;

import org.springframework.stereotype.Component;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.domain.User;

/**
 * Interface which defines methods which will be implemented in the
 * "UserServiceImpl". This interface is reusable, so other classes could extend
 * this if they wanted / needed to.
 * 
 * @author Tim Flucker
 *
 */
@Component
public interface UserService {

	/**
	 * Used to verify the association between the User and the Bank Account.
	 * 
	 * @param id
	 * @return User
	 */
	public User findUserById(long id);

	/**
	 * Creates a new record in the USERS table with the provided method parameters.
	 * These parameters are validated and if the validations fail, then no record is
	 * saved and an error is returned.
	 * 
	 * @param userInput
	 * @return GenericApiResponse
	 */
	public GenericApiResponse createUser(User userInput);

}
