package edu.bu.metcs673.trackr.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.common.CommonConstants;
import net.minidev.json.JSONObject;

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

		JSONObject extra = TrackrUserController.createTokenObject(MOCK_TOKEN_VAL);
		ResponseEntity<GenericApiResponse<JSONObject>> expected = new ResponseEntity<>(
				GenericApiResponse.successResponse(CommonConstants.CREATE_USER_SUCCESS, extra),
				HttpStatus.OK);

		ResponseEntity<GenericApiResponse<JSONObject>> actual = controller.createUser(testUser);

		// Field by field comparison ignoring the extra field since it's not evaluated correctly being a raw object
		assertThat(actual)
				.usingRecursiveComparison()
				.ignoringFields("additionalData")
				.isEqualTo(expected);

		// Explicit check the extra field
		assertThat(actual.getBody().getAdditionalData())
				.usingRecursiveComparison()
				.isEqualTo(expected.getBody().getAdditionalData());
	}
}
