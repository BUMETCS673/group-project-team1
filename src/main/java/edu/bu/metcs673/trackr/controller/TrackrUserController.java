package edu.bu.metcs673.trackr.controller;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.api.TrackrUserDTO;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.domain.TrackrUser;
import edu.bu.metcs673.trackr.security.JWTUtil;
import edu.bu.metcs673.trackr.service.TrackrUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller for Users Management. Contains a 'Create' API for new users to
 * register with the application. Also contains an API which generates a new JWT
 * token for the user to use for authentication.
 * 
 * @author Tim Flucker
 *
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/users")
public class TrackrUserController {

	@Autowired
	private TrackrUserService userService;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Using provided request body, creates a new record in the USERS table. Request
	 * body is validated, and an encrypted password value is saved. Returns a
	 * success message with a JWT token.
	 * 
	 * @param userInput
	 * @return GenericApiResponse
	 */
	@PostMapping
	public ResponseEntity<GenericApiResponse> createUser(@Valid @RequestBody TrackrUserDTO userInput) {
		String token = userService.createUser(userInput);

		return new ResponseEntity<GenericApiResponse>(
				GenericApiResponse.successResponse(CommonConstants.CREATE_USER_SUCCESS + token), HttpStatus.OK);
	}

	/**
	 * Using the provided valid request body, authenicate the user and if
	 * credentials are correct, then provide a new JWT token for other API use.
	 * 
	 * @param userLogin
	 * @return
	 */
	@PostMapping("/retrieveToken")
	public ResponseEntity<GenericApiResponse> retrieveToken(@Valid @RequestBody TrackrUser userLogin) {

		try {
			UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
					userLogin.getUsername(), userLogin.getPassword());

			authenticationManager.authenticate(authInputToken);

			String token = jwtUtil.generateToken(userLogin.getUsername());

			return new ResponseEntity<GenericApiResponse>(
					GenericApiResponse.successResponse(CommonConstants.NEW_JWT_TOKEN + token), HttpStatus.OK);

		} catch (AuthenticationException e) {
			log.error("Invalid Login Credentials ...");
			return new ResponseEntity<GenericApiResponse>(
					GenericApiResponse.errorResponse(CommonConstants.INVALID_CREDENTIALS), HttpStatus.UNAUTHORIZED);
		}

	}
}
