package edu.bu.metcs673.trackr.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TrackrUserTest {

	public static final String TEST_FIRST_NAME = "John";
	public static final String TEST_LAST_NAME = "Doe";
	public static final String TEST_USERNAME = "johnDoe";
	public static final String TEST_PASSWORD = "password";
	public static final String TEST_EMAIL = "johnDoe@email.com";

	private static Validator validator;

	@BeforeAll
	public static void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void getterSetterTest() {
		TrackrUser user = new TrackrUser();

		// verify that blank constructor sets default values (null)
		assertNull(user.getFirstName());
		assertNull(user.getLastName());
		assertNull(user.getUsername());
		assertNull(user.getPassword());
		assertNull(user.getEmail());

		user.setFirstName(TEST_FIRST_NAME);
		user.setLastName(TEST_LAST_NAME);
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		user.setEmail(TEST_EMAIL);

		// verify that the setters have modified the values
		assertEquals(TEST_FIRST_NAME, user.getFirstName());
		assertEquals(TEST_LAST_NAME, user.getLastName());
		assertEquals(TEST_USERNAME, user.getUsername());
		assertEquals(TEST_PASSWORD, user.getPassword());
		assertEquals(TEST_EMAIL, user.getEmail());
	}

	@Test
	public void emptyConstructorTest() {
		TrackrUser user = new TrackrUser();

		// verify that blank constructor sets default values (null)
		assertNull(user.getFirstName());
		assertNull(user.getLastName());
		assertNull(user.getUsername());
		assertNull(user.getPassword());
		assertNull(user.getEmail());
	}

	@Test
	public void allArgsConstructorTest() {
		TrackrUser user = new TrackrUser(1L, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_USERNAME, TEST_PASSWORD, TEST_EMAIL);

		// verify that all fields are set from the constructor
		assertEquals(TEST_FIRST_NAME, user.getFirstName());
		assertEquals(TEST_LAST_NAME, user.getLastName());
		assertEquals(TEST_USERNAME, user.getUsername());
		assertEquals(TEST_PASSWORD, user.getPassword());
		assertEquals(TEST_EMAIL, user.getEmail());
	}

	@ParameterizedTest
	@MethodSource("generateTestData")
	public void testAnnotationBasedValidations(TrackrUserDTO userDTO) {

		Set<ConstraintViolation<TrackrUserDTO>> violations = validator.validate(userDTO);
		assertFalse(violations.isEmpty());
	}

	/**
	 * Creates different objects to be used in the parameterized test. Used to test
	 * the 'validate' method.
	 * 
	 * @return
	 */
	private static Stream<Arguments> generateTestData() {
		return Stream.of(Arguments.of(new TrackrUserDTO()),
				Arguments.of(new TrackrUserDTO(null, "mcTesterson", "tester00", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new TrackrUserDTO("testy", null, "tester01", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new TrackrUserDTO("testy", "mcTesterson", "tester03", "myCoolPassword", null)),
				Arguments.of(new TrackrUserDTO("", "", "", "", "")),
				Arguments.of(new TrackrUserDTO("", "mcTesterson", "tester04", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new TrackrUserDTO("testy", "", "tester05", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new TrackrUserDTO("testy", "mcTesterson", "tester07", "myCoolPassword", "")),

				// length tests
				Arguments.of(new TrackrUserDTO(
						"testyasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadfasdfasdfsadfasdfasdf",
						"mcTesterson", "tester08", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new TrackrUserDTO("testy",
						"mcTestersonasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadf",
						"tester09", "myCoolPassword", ")#(*$)@(##@()*")),
				Arguments.of(new TrackrUserDTO("testy", "mcTesterson",
						"tester00asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf",
						"myCoolPassword", "testEmail@email.com")),
				Arguments.of(new TrackrUserDTO("testy", "mcTesterson", "tester10",
						"myCoolPasswordasdfasdfasdfasdfasdfasdfasdfasdfkljglkashvpawdvkjbaskjvhpweifpoiewnfshdvuashdpvoijasdkvnpas988hvjoaiwjegdafsdfasi2bgpviasjlvdjnlwkbfsjdbaoidngkksjadbhp9asdjoasdgasdfasdfashdjkshkvajhskljdvhlaskdjfhlkahjdsf",
						"testEmail@email.com")),
				Arguments.of(new TrackrUserDTO("testy", "mcTesterson", "tester11", "myCoolPassword",
						"testEasdfasdfasdfasdfasdfasdfasdfasdfasdfsadfmail@emasdfasdfasdfasasdfasdfsdfasdfasdfasdfasdfdfasdfasail.com")),

				// bad email format
				Arguments.of(new TrackrUserDTO("testy", "mcTesterson", "tester12", "myCoolPassword",
						"#@$@#$!@$!@*^&*!@^#")));
	}
}