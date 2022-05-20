package edu.bu.metcs673.trackr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.common.TrackrInputValidationException;
import edu.bu.metcs673.trackr.domain.User;
import edu.bu.metcs673.trackr.repo.UserRepository;
import edu.bu.metcs673.trackr.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * Defines logic of the "UserService" methods. Calls methods in the
 * 'UserRepository' class to get / save data. JavaDocs for overridden methods
 * are in the UserService class.
 * 
 * @author Tim Flucker
 *
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findUserById(long id) {
		log.info("Searching for record id: " + id);
		User user = userRepository.findById(id).get();
		return user;
	}

	@Override
	public GenericApiResponse createUser(User userInput) {
		// validate parameters
		validateParameters(userInput);

		// TODO: hash value for password field to increase security

		// saves new User record in DB
		userRepository.save(userInput);

		// return response entity with a success response
		return GenericApiResponse.successResponse(CommonConstants.CREATE_USER_SUCCESS);
	}

	/**
	 * Validates the parameters of the User that cannot be covered using
	 * annotations.
	 * 
	 * @param userInput
	 */
	public void validateParameters(User userInput) {

		// check that username is unique
		if (userRepository.existsByUsername(userInput.getUsername())) {
			throw new TrackrInputValidationException(CommonConstants.DUPLICATE_USERNAME);
		}

	}

}
