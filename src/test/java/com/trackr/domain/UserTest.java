package com.trackr.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class UserTest {

	public static final String TEST_USERNAME = "johnDoe";
	public static final String TEST_PASSWORD = "password";
	public static final String TEST_EMAIL = "johnDoe@email.com";

	@Test
	public void getterSetterTest() {
		User user = new User();

		// verify that blank constructor sets default values (null)
		assertNull(user.getUsername());
		assertNull(user.getPassword());
		assertNull(user.getEmail());
		
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		user.setEmail(TEST_EMAIL);
		
		// verify that the setters have modified the values
		assertEquals(TEST_USERNAME, user.getUsername());
		assertEquals(TEST_PASSWORD, user.getPassword());
		assertEquals(TEST_EMAIL, user.getEmail());
	}
	
	@Test
	public void emptyConstructorTest() {
		User user = new User();

		// verify that blank constructor sets default values (null)
		assertNull(user.getUsername());
		assertNull(user.getPassword());
		assertNull(user.getEmail());
	}
	
	@Test
	public void allArgsConstructorTest() {
		User user = new User(1, TEST_USERNAME, TEST_PASSWORD, TEST_EMAIL);
		
		// verify that all fields are set from the constructor
		assertEquals(TEST_USERNAME, user.getUsername());
		assertEquals(TEST_PASSWORD, user.getPassword());
		assertEquals(TEST_EMAIL, user.getEmail());
	}

}
