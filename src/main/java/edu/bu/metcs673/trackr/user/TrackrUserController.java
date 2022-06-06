package edu.bu.metcs673.trackr.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.security.JWTUtil;
import net.minidev.json.JSONObject;

/**
 * Controller for Users Management. Contains a 'Create' API for new users to
 * register with the application. Also contains an API which generates a new JWT
 * token for the user to use for authentication.
 * 
 * @author Tim Flucker
 *
 */
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

		JSONObject tokenObj = createTokenObject(token);

		return new ResponseEntity<GenericApiResponse>(
				GenericApiResponse.successResponse(CommonConstants.CREATE_USER_SUCCESS, tokenObj), HttpStatus.OK);
	}

	/**
	 * Using the provided valid request body, authenicate the user and if
	 * credentials are correct, then provide a new JWT token for other API use.
	 * 
	 * @param userLogin
	 * @return
	 */
	@PostMapping("/retrieve-token")
	public ResponseEntity<GenericApiResponse> retrieveToken(@Valid @RequestBody TokenRetrievalDTO userLogin) {

		try {
			UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
					userLogin.getUsername(), userLogin.getPassword());

			Authentication auth = authenticationManager.authenticate(authInputToken);

			String token = jwtUtil.generateToken(((UserDetails) auth.getPrincipal()).getUsername());

			JSONObject tokenObj = createTokenObject(token);

			return new ResponseEntity<GenericApiResponse>(
					GenericApiResponse.successResponse(CommonConstants.NEW_JWT_TOKEN, tokenObj), HttpStatus.OK);

		} catch (AuthenticationException e) {
			return new ResponseEntity<GenericApiResponse>(
					GenericApiResponse.errorResponse(CommonConstants.INVALID_CREDENTIALS), HttpStatus.UNAUTHORIZED);
		}

	}

	/**
	 * Helper method to present the JWT Token value to the user in a more organized
	 * way.
	 * 
	 * @param token JSON Web Token string
	 * @return A JSON object
	 */
	public static JSONObject createTokenObject(String token) {
		JSONObject tokenObj = new JSONObject();
		tokenObj.put("jwtToken", token);
		return tokenObj;
	}
}
