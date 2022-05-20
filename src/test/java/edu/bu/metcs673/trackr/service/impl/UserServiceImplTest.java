package edu.bu.metcs673.trackr.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.bu.metcs673.trackr.common.TrackrInputValidationException;
import edu.bu.metcs673.trackr.domain.User;

@SpringBootTest
public class UserServiceImplTest {

	@Autowired
	private UserServiceImpl serviceImpl;

	@Test
	public void testDuplicateUsername() {
		User testUser = new User(0L, "testy", "mcTesterson", "tflucker", "myCoolPassword", "testEmail@email.com");
		assertThrows(TrackrInputValidationException.class, () -> serviceImpl.validateParameters(testUser));
	}

}
