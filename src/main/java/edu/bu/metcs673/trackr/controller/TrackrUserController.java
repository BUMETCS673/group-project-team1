package edu.bu.metcs673.trackr.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.domain.TrackrUser;
import edu.bu.metcs673.trackr.security.JWTUtil;
import edu.bu.metcs673.trackr.service.TrackrUserService;
import lombok.extern.slf4j.Slf4j;

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
	 * 
	 * @param userInput
	 * @return
	 */
	@PostMapping
	public ResponseEntity<GenericApiResponse> createUser(@Valid @RequestBody TrackrUser userInput) {
		String token = userService.createUser(userInput);

		return new ResponseEntity<GenericApiResponse>(
				GenericApiResponse.successResponse(CommonConstants.CREATE_USER_SUCCESS + token), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<TrackrUser> getUserInfo() {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return new ResponseEntity<TrackrUser>(userService.findByUsername(username), HttpStatus.OK);
	}

	@PostMapping("/retrieveToken")
	public String retrieveToken(@RequestBody TrackrUser userLogin) {

		try {
			UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
					userLogin.getUsername(), userLogin.getPassword());

			authenticationManager.authenticate(authInputToken);

			String token = jwtUtil.generateToken(userLogin.getUsername());

			return token;
		} catch (AuthenticationException e) {
			log.error("Invalid Login Credentials ...");
			throw new RuntimeException();
		}

	}
}