package edu.bu.metcs673.trackr.service.impl;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * 'UserRepository' class to get / save data. JavaDocs for overridden methods are
 * in the UserService class.
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
	public List<User> retrieveAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserById(long id) {
		log.info("Searching for record id: " + id);
		User user = userRepository.findById(id).get();
		return user;
	}

	@Override
	public ResponseEntity<GenericApiResponse> createUser(User userInput) {
		// validate parameters
		validateParameters(userInput);

		// TODO: hash value for password field to increase security

		// saves new User record in DB
		userRepository.save(userInput);

		// return response entity with a success response
		return new ResponseEntity<GenericApiResponse>(
				GenericApiResponse.successResponse(CommonConstants.CREATE_USER_SUCCESS), HttpStatus.ACCEPTED);
	}

	/**
	 * Validates the parameters of the User (username, password, email). Checks that
	 * all have value, that username is unique, and that email has a valid format.
	 * 
	 * @param userInput
	 */
	public void validateParameters(User userInput) {

		String username = userInput.getUsername();
		String password = userInput.getPassword();
		String email = userInput.getEmail();

		// since all of these fields are required, check that they have some value
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(email)) {
			throw new TrackrInputValidationException(CommonConstants.NULL_PARAMETERS);
		}

		// check that username is unique
		if (userRepository.existsByUsername(username)) {
			throw new TrackrInputValidationException(CommonConstants.DUPLICATE_USERNAME);
		}

		// check that email has proper format (ex. myEmail@email.com)
		String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		if (!Pattern.matches(emailRegex, email)) {
			throw new TrackrInputValidationException(CommonConstants.INVALID_EMAIL_FORMAT);
		}
	}

}
