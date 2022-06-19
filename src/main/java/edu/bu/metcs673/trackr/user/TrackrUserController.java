package edu.bu.metcs673.trackr.user;

import static edu.bu.metcs673.trackr.common.CommonConstants.JWT_COOKIE_MAX_AGE_MINUTES;
import static edu.bu.metcs673.trackr.common.CommonConstants.JWT_COOKIE_NAME;
import static edu.bu.metcs673.trackr.common.CommonConstants.JWT_COOKIE_PATH;
import static edu.bu.metcs673.trackr.common.CommonConstants.USER_LOGGED_IN_COOKIE_NAME;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
			Cookie jwtCookie = new Cookie(JWT_COOKIE_NAME, token);

			// Accessible by JS since it's a simple flag cannot be used with impersonate user without JWT
			Cookie userLoggedInCookie = new Cookie(USER_LOGGED_IN_COOKIE_NAME, "true");

			// We set HttpOnly so that JavaScript cannot read it top prevent XSS attack
			// we set the secure attribute to further increase security
			jwtCookie.setHttpOnly(true);
			jwtCookie.setSecure(true);
			
			userLoggedInCookie.setSecure(true);
			
			// Set path to root so that it's available to the whole application domain
			jwtCookie.setPath(JWT_COOKIE_PATH);
			userLoggedInCookie.setPath(JWT_COOKIE_PATH);

			// Set max age to be 15 minutes
			jwtCookie.setMaxAge(JWT_COOKIE_MAX_AGE_MINUTES);
			userLoggedInCookie.setMaxAge(JWT_COOKIE_MAX_AGE_MINUTES);

			// Add cookies to Servlet Response to return to browser
			response.addCookie(jwtCookie);
			response.addCookie(userLoggedInCookie);

			return ResponseEntity.ok()
					.body(GenericApiResponse.successResponse(CommonConstants.NEW_JWT_TOKEN, tokenObj));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(GenericApiResponse.errorResponse(CommonConstants.INVALID_CREDENTIALS, null));
		}
	}

	/**
	 * Get current user profile. This is a secure path since the filter will run and fetch the token.
	 *
	 * @return User DTO presentation to avoid depending on model for views.
	 */
	@GetMapping("/api/v1/profile")
	public ResponseEntity<GenericApiResponse<TrackrUserDTO>> getProfile() {
		TrackrUserDTO dto = userService.getCurrentUserProfile();
		return ResponseEntity.ok(GenericApiResponse.successResponse(CommonConstants.GET_USER_PROFILE_SUCCESS, dto));
	}

	/**
	 * Update user profile but only mutable fields in that context. Change password is different flow.
	 *
	 * @param dto User DTO
	 * @return Response
	 */
	@PutMapping("/api/v1/profile")
	public ResponseEntity<GenericApiResponse<TrackrUserDTO>> updateProfile(@Valid @RequestBody TrackrUserDTO dto) {
		TrackrUserDTO updated = userService.updateUser(dto);
		return ResponseEntity.ok(GenericApiResponse.successResponse(CommonConstants.UPDATE_USER_PROFILE_SUCCESS, updated));
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