package edu.bu.metcs673.trackr.user;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.common.TrackrInputValidationException;
import edu.bu.metcs673.trackr.security.JWTUtil;

/**
 * Defines logic of the "UserService" methods. Calls methods in the
 * 'UserRepository' class to get / save data. JavaDocs for overridden methods
 * are in the UserService class.
 * 
 * @author Tim Flucker
 *
 */
@Service
public class TrackrUserServiceImpl implements TrackrUserService, UserDetailsService {

	@Autowired
	private TrackrUserRepository userRepository;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public TrackrUser findUserById(long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public TrackrUser findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public String createUser(TrackrUserDTO userInput) {
		// validate parameters
		validateParameters(userInput);

		// encrypt the provided password value, update User object
		String encodedPwd = bCryptPasswordEncoder.encode(userInput.getPassword());

		// saves new User record in DB
		TrackrUser user = userRepository.save(new TrackrUser(0L, userInput.getFirstName(), userInput.getLastName(),
				userInput.getUsername(), encodedPwd, userInput.getEmail()));

		String token = jwtUtil.generateToken(user.getUsername());

		// return response entity with a success response
		return token;
	}

	/**
	 * Validates the parameters of the User that cannot be covered using
	 * annotations.
	 * 
	 * @param userInput
	 */
	public void validateParameters(TrackrUserDTO userInput) {

		// check that username is unique
		if (userRepository.existsByUsername(userInput.getUsername())) {
			throw new TrackrInputValidationException(CommonConstants.DUPLICATE_USERNAME);
		}

	}

	/**
	 * Used by the UserDetailsService when the JWT filter logic is occurring.
	 * Searches the USERS database table based on the provided username and then
	 * creates a Spring Security 'User' object based on that information.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {

		TrackrUser trackrUser = userRepository.findByUsername(username);

		if (trackrUser == null) {
			throw new UsernameNotFoundException(CommonConstants.INVALID_TOKEN);
		}
		return new User(username, trackrUser.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

	}

}