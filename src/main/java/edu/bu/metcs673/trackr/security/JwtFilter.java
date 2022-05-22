package edu.bu.metcs673.trackr.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import edu.bu.metcs673.trackr.service.impl.TrackrUserServiceImpl;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private TrackrUserServiceImpl userServiceImpl;

	@Autowired
	private JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && !authHeader.isEmpty() && authHeader.startsWith("Bearer ")) {
			String jwt = authHeader.substring(7);
			if (jwt == null || StringUtils.isBlank(jwt)) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
			} else {
				try {

					String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);
					if(!StringUtils.isBlank(username)) {
						UserDetails userDetails = userServiceImpl.loadUserByUsername(username);

						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
								userDetails.getPassword(), userDetails.getAuthorities());

						if (SecurityContextHolder.getContext().getAuthentication() == null) {
							SecurityContextHolder.getContext().setAuthentication(authToken);
						}
					} 					

				} catch (JWTVerificationException e) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
				}
			}
		}
		
		chain.doFilter(request, response);
	}
}
