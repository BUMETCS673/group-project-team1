package edu.bu.metcs673.trackr.user;

import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.common.TrackrInputValidationException;
import edu.bu.metcs673.trackr.security.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * Defines logic of the "UserService" methods. Calls methods in the
 * 'UserRepository' class to get / save data. JavaDocs for overridden methods
 * are in the UserService class.
 *
 * @author Tim Flucker
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

    /**
     * {@inheritDoc}
     */
    @Override
    public TrackrUser getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userRepository.findByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrackrUserDTO getCurrentUserProfile() {
        TrackrUser model = getCurrentUser();
        TrackrUserDTO dto = new TrackrUserDTO();

        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setEmail(model.getEmail());
        dto.setUsername(model.getUsername());

        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createUser(TrackrUserDTO userInput) {

        // validate parameters
        validateParameters(userInput);

		regexNameValidation(userInput.getFirstName());
		regexNameValidation(userInput.getLastName());
        regexPasswordValidation(userInput.getPassword());
		
        // encrypt the provided password value, update User object
        String encodedPwd = bCryptPasswordEncoder.encode(userInput.getPassword());

        // saves new User record in DB
        TrackrUser user = userRepository.save(new TrackrUser(0L, userInput.getFirstName(), userInput.getLastName(),
                userInput.getUsername(), encodedPwd, userInput.getEmail()));

        // return response entity with a success response
        return jwtUtil.generateToken(user.getUsername());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TrackrUserDTO updateUser(TrackrUserDTO dto) {
        TrackrUser trackrUser = getCurrentUser();

        if (StringUtils.isNotBlank(dto.getNewPassword())) {
            regexPasswordValidation(dto.getNewPassword());
            if (!bCryptPasswordEncoder.matches(dto.getPassword(), trackrUser.getPassword())) {
                throw new TrackrInputValidationException(CommonConstants.WRONG_PASSWORD);
            } else {
                trackrUser.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
            }
        }
        regexNameValidation(dto.getFirstName());
        regexNameValidation(dto.getLastName());

        trackrUser.setFirstName(dto.getFirstName());
        trackrUser.setLastName(dto.getLastName());
        trackrUser.setEmail(dto.getEmail());

        trackrUser = userRepository.save(trackrUser);

        TrackrUserDTO updated = new TrackrUserDTO();
        updated.setFirstName(trackrUser.getFirstName());
        updated.setLastName(trackrUser.getLastName());
        updated.setEmail(trackrUser.getEmail());
        updated.setUsername(trackrUser.getUsername());

        return updated;
    }

    /**
     * Validates the parameters of the User that cannot be covered using annotations.
     *
     * @param userInput User DTO
     */
    public void validateParameters(TrackrUserDTO userInput) {

        // check that username is unique
        if (userRepository.existsByUsername(userInput.getUsername())) {
            throw new TrackrInputValidationException(CommonConstants.DUPLICATE_USERNAME);
        }
        
		if (userRepository.existsByEmail(userInput.getEmail())) {
			throw new TrackrInputValidationException(CommonConstants.DUPLICATE_EMAIL);

		}
    }


	/**
	 * Validates that a string value only contains alphanumeric input
	 * 
	 * @param name
	 */
	public void regexNameValidation(String name) {

		if (!Pattern.matches(CommonConstants.NAME_REGEX, name)) {
			throw new TrackrInputValidationException(MessageFormat.format(CommonConstants.INVALID_CHARACTERS, name));
		}
	}

    public void regexPasswordValidation(String password) {

        if (!Pattern.matches(CommonConstants.PASSWORD_REGEX, password)) {
            throw new TrackrInputValidationException(MessageFormat.format(CommonConstants.INVALID_CHARACTERS, password));
        }
    }
	
    /**
     * Used by the UserDetailsService when the JWT filter logic is occurring.
     * Searches the USERS database table based on the provided username and then
     * creates a Spring Security 'User' object based on that information.
     *
     * @param username Username to do the lookup by
     * @return User details
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
