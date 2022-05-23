package edu.bu.metcs673.trackr.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.bu.metcs673.trackr.api.TrackrUserDTO;
import edu.bu.metcs673.trackr.common.TrackrInputValidationException;

@SpringBootTest
public class TrackrUserServiceImplTest {

	@Autowired
	private TrackrUserServiceImpl serviceImpl;

	@Test
	public void testDuplicateUsername() {
		TrackrUserDTO testUser = new TrackrUserDTO("testy", "mcTesterson", "tflucker", "myCoolPassword", "testEmail@email.com");
		assertThrows(TrackrInputValidationException.class, () -> serviceImpl.validateParameters(testUser));
	}

}
