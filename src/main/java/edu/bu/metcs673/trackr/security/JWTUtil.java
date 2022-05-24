package edu.bu.metcs673.trackr.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

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

	public String generateToken(String username) {
		return JWT.create().withSubject("User Details").withClaim("username", username).withIssuedAt(new Date())
				.withIssuer("Trackr Application").sign(Algorithm.HMAC256(jwtSecret));
	}

	public String validateTokenAndRetrieveSubject(String token) {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).withSubject("User Details")
				.withIssuer("Trackr Application").build();

		DecodedJWT jwt = verifier.verify(token);
		return jwt.getClaim("username").asString();
	}
}
