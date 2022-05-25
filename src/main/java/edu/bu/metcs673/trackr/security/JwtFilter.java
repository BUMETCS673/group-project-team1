package edu.bu.metcs673.trackr.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import edu.bu.metcs673.trackr.service.impl.TrackrUserServiceImpl;

/**
 * Filter class that runs before actual API logic is processed. Checks for a JWT
 * token and tries to verify it. Successfully verification will pass the request
 * to its respective controller, while unsuccessful verification will return an
 * unathorized response to the user.
 * 
 * Reference:
 * https://medium.com/geekculture/implementing-json-web-token-jwt-authentication-using-spring-security-detailed-walkthrough-1ac480a8d970
 * 
 * @author Tim Flucker
 *
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private TrackrUserServiceImpl userServiceImpl;
	
	@Autowired
	private JWTUtil jwtUtil;

	/**
	 * Filter method that checks for the JWT authorization, if it finds a JWT bearer
	 * token then verification will take place, otherwise an access denied error is
	 * returned.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String authHeader = request.getHeader("Authorization");

		// if request has an Authorization header which is a Bearer token (JWT) then attempt to verify it
		if (authHeader != null && !authHeader.isEmpty() && authHeader.startsWith("Bearer ")) {
			String jwt = authHeader.substring(7);
			if (jwt == null || StringUtils.isBlank(jwt)) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
			} else {
				try {

					// validate token and return the username contained within
					String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);
					if (StringUtils.isNotBlank(username)) {
//						TrackrUser userDetails = userRepository.findByUsername(username);
						UserDetails userDetails = userServiceImpl.loadUserByUsername(username);

						
						// create token using the username and password gathered from the JWT token
						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
								username, userDetails.getPassword(), new ArrayList<>());

						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						
						// set authentication for API request
						if (SecurityContextHolder.getContext().getAuthentication() == null) {
							SecurityContextHolder.getContext().setAuthentication(authToken);
						}
					}

				} catch (JWTVerificationException | UsernameNotFoundException e) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
					return;
				}
			}
		}

		chain.doFilter(request, response);
	}
}
