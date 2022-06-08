package edu.bu.metcs673.trackr.user;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.security.JWTUtil;
import net.minidev.json.JSONObject;
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
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static edu.bu.metcs673.trackr.common.CommonConstants.JWT_COOKIE_MAX_AGE_MINUTES;
import static edu.bu.metcs673.trackr.common.CommonConstants.JWT_COOKIE_NAME;
import static edu.bu.metcs673.trackr.common.CommonConstants.JWT_COOKIE_PATH;

/**
 * Controller for Users Management. Contains a 'Create' API for new users to
 * register with the application. Also contains an API which generates a new JWT
 * token for the user to use for authentication.
 *
 * @author Tim Flucker
 */
@Validated
@RestController
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
	@PostMapping("/register")
	public ResponseEntity<GenericApiResponse<JSONObject>> createUser(@Valid @RequestBody TrackrUserDTO userInput) {
		String token = userService.createUser(userInput);
		JSONObject tokenObj = createTokenObject(token);
		return ResponseEntity.ok()
				.body(GenericApiResponse.successResponse(CommonConstants.CREATE_USER_SUCCESS, tokenObj));
	}

	/**
	 * Using the provided valid request body, authenicate the user and if
	 * credentials are correct, then provide a new JWT token for other API use.
	 *
	 * @param userLogin
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<GenericApiResponse<JSONObject>> login(@Valid @RequestBody TokenRetrievalDTO userLogin,
			HttpServletResponse response) {

		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
			String token = jwtUtil.generateToken(((UserDetails) auth.getPrincipal()).getUsername());
			JSONObject tokenObj = createTokenObject(token);

			// Return the JWT to the browser as cookie
			Cookie cookie = new Cookie(JWT_COOKIE_NAME, token);

			// We set HttpOnly so that JavaScript cannot read it top prevent XSS attack
			cookie.setHttpOnly(true);

			// Set path to root so that it's available to the whole application domain
			cookie.setPath(JWT_COOKIE_PATH);

			// Set max age to be 15 minutes
			cookie.setMaxAge(JWT_COOKIE_MAX_AGE_MINUTES);

			// Add Servlet Response to return to browser
			response.addCookie(cookie);

			return ResponseEntity.ok()
					.body(GenericApiResponse.successResponse(CommonConstants.NEW_JWT_TOKEN, tokenObj));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(GenericApiResponse.errorResponse(CommonConstants.INVALID_CREDENTIALS, null));
		}
	}

	/**
	 * Helper method to present the JWT Token value to the user in a more organized
	 * way.
	 *
	 * @param token JSON Web Token string
	 * @return A JSON object
	 */
	static JSONObject createTokenObject(String token) {
		JSONObject tokenObj = new JSONObject();
		tokenObj.put("jwtToken", token);
		return tokenObj;
	}
}