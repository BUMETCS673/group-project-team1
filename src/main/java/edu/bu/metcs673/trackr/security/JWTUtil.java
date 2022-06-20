package edu.bu.metcs673.trackr.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * Utility class that generates a JWT token, or validates it during the
 * authentication process.
 * 
 * Reference:
 * https://medium.com/geekculture/implementing-json-web-token-jwt-authentication-using-spring-security-detailed-walkthrough-1ac480a8d970
 * 
 * @author Tim Flucker
 *
 */
@Component
public class JWTUtil {

	@Value("${jwt_secret}")
	public String jwtSecret;

	/**
	 * Add constructor for testing.
	 *
	 * @param jwtSecret Secret
	 */
	public JWTUtil(@Value("${jwt_secret}") String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	/**
	 * Method to create a new JWT token with Subject, Claim, Issued At, Issuer
	 * sections and sign with a secret using a secret value to make it unique.
	 * Provided username parameter is used for the Claim section of the token.
	 * 
	 * @param username
	 * @return String token
	 */
	public String generateToken(String username) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
		
		return JWT.create().withSubject("User Details")
				.withClaim("username", username)
				.withClaim("authorities", 
						grantedAuthorities.stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.withIssuedAt(new Date())
				.withIssuer("Trackr Application").sign(Algorithm.HMAC256(jwtSecret));
	}

	/**
	 * Validate the the provided token string is valid. This is determined by
	 * checking to see if it was created using the same algorithm, and contains a
	 * matching subject and issuer values. Return 'username' value in token as a
	 * string.
	 * 
	 * @param token
	 * @return String username
	 */
	public String validateTokenAndRetrieveSubject(String token) {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).withSubject("User Details")
				.withIssuer("Trackr Application").build();

		DecodedJWT jwt = verifier.verify(token);
		return jwt.getClaim("username").asString();
	}
}
