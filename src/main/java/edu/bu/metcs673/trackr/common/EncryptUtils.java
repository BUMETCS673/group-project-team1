package edu.bu.metcs673.trackr.common;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * Contains code for hashing a String password value to more securely protect
 * user login data.
 * Reference: https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
 * 
 * @author Tim Flucker
 *
 */
public class EncryptUtils {

	// TODO: FINISH THIS
	public static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		byte[] salt = new byte[16];
		
		sr.nextBytes(salt);
		
		return salt.toString();
	}
}
