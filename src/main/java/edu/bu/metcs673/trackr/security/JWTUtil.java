package edu.bu.metcs673.trackr.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

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
