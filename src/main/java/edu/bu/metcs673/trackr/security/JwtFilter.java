package edu.bu.metcs673.trackr.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import edu.bu.metcs673.trackr.user.TrackrUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static edu.bu.metcs673.trackr.common.CommonConstants.JWT_COOKIE_NAME;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * Filter class that runs before actual API logic is processed. Checks for a JWT token and tries to
 * verify it. Successfully verification will pass the request to its respective controller, while
 * unsuccessful verification will return an unathorized response to the user.
 * <p>
 * Reference:
 * https://medium.com/geekculture/implementing-json-web-token-jwt-authentication-using-spring-security-detailed-walkthrough-1ac480a8d970
 *
 * @author Tim Flucker
 * @author Jean Dorancy
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final TrackrUserServiceImpl userServiceImpl;
    private final JWTUtil jwtUtil;

    /**
     * JWT Filter constructor
     *
     * @param userServiceImpl User service
     * @param jwtUtil         JWT Util
     */
    public JwtFilter(@Autowired TrackrUserServiceImpl userServiceImpl, @Autowired JWTUtil jwtUtil) {
        this.userServiceImpl = userServiceImpl;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Filter method that checks for the JWT authorization, if it finds a JWT then verification otherwise unauthorized.
     * Note: There are certain paths that should skip the filter see {@link #shouldNotFilter(HttpServletRequest)}.
     *
     * @param request  Http Servlet Request
     * @param response Http Servlet Response
     * @param chain    Filter chain which has to be called in order apply subsequent filters
     * @throws IOException      Checked exception
     * @throws ServletException Checked exception
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {

		// Fetch the token
        String jwt = getJwt(request);

        // If the  token was present either in header or cookie but, it was a blank string.
        if (StringUtils.isBlank(jwt)) {
            response.sendError(SC_UNAUTHORIZED, "Invalid JSON Web Token (JWT).");
            return;
        }

        try {

            // Validate token and return the username contained within
            String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);
            if (StringUtils.isNotBlank(username)) {
                UserDetails userDetails = userServiceImpl.loadUserByUsername(username);

                // Create token using the username and password gathered from the JWT token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username,
                        userDetails.getPassword(),
                        new ArrayList<>()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication for API request
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (JWTVerificationException | UsernameNotFoundException e) {
            response.sendError(SC_UNAUTHORIZED, "Invalid JWT Token");
            return;
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }

    /**
     * Checks for the paths the filter should skip. Avoid giving permission to range of pages.
     * <a href="https://www.baeldung.com/spring-exclude-filter">Should Not Filter Tutorial</a>
     *
     * @param request Servlet request
     * @return boolean
     * @throws ServletException Checked exception
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<Pattern> patterns = Arrays.asList(
                Pattern.compile("/favicon.ico"), // Images from static assets
                Pattern.compile("/images/.*"), // Images from static assets
                Pattern.compile("/built/.*"), // Generate assets from Webpack build
                Pattern.compile("/"), // Index page
                Pattern.compile("/register"), // Registration page
                Pattern.compile("/login"), // login page
                Pattern.compile("/logout") // Logout page
        );

        // If we match one then we should not filter
        for (Pattern pattern : patterns) {
            if (pattern.matcher(request.getRequestURI()).matches()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get the JWT from the request. It can come from a cookie or Http Authorization header.
     *
     * @param request Http request
     * @return String
     */
    private String getJwt(HttpServletRequest request) {
        String authHeader = Optional.ofNullable(request.getHeader("Authorization")).orElse("");
        String jwt = "";

        // If the request has an Authorization header which is a Bearer token (JWT) then attempt to verify it
        if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        // If no header then check the cookie
        if (StringUtils.isBlank(jwt)) {
            Cookie cookie = WebUtils.getCookie(request, JWT_COOKIE_NAME);
            jwt = Optional.ofNullable(cookie).map(Cookie::getValue).orElse("");
        }

        return jwt;
    }
}
