package edu.bu.metcs673.trackr.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.api.TrackrUserDTO;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.service.TrackrUserService;

@ExtendWith(MockitoExtension.class)
public class TrackrUserControllerTest {

	public static final String MOCK_TOKEN_VAL = "SOME_COOL_TOKEN_VAL";

	@Mock
	private TrackrUserService userService;

	@InjectMocks
	private TrackrUserController controller = new TrackrUserController();

	@Test
	public void userAnnotationValidationTest() {

		TrackrUserDTO testUser = new TrackrUserDTO("test", "Tester", "tTester", "blahblah", "test@email.com");
		Mockito.when(userService.createUser(testUser)).thenReturn(MOCK_TOKEN_VAL);

		ResponseEntity<GenericApiResponse> mockResponse = new ResponseEntity<GenericApiResponse>(
				GenericApiResponse.successResponse(CommonConstants.CREATE_USER_SUCCESS + MOCK_TOKEN_VAL),
				HttpStatus.OK);

		assertEquals(controller.createUser(testUser), mockResponse);

	}

}
