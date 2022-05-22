package edu.bu.metcs673.trackr.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.bu.metcs673.trackr.domain.TrackrUser;

@SpringBootTest
public class TrackrUserControllerTest {

	@Autowired
	TrackrUserController controller = new TrackrUserController();

	@ParameterizedTest
	@MethodSource("generateTestUsers")
	public void userAnnotationValidationTest(TrackrUser testUser) {

		assertThrows(ConstraintViolationException.class, () -> controller.createUser(testUser));
	}

	/**
	 * Creates different objects to be used in the parameterized test. Used to test
	 * the 'validate' method.
	 * 
	 * @return
	 */
	private static Stream<Arguments> generateTestUsers() {
		return Stream.of(Arguments.of(
				// null and empty tests
				new TrackrUser()),
				Arguments.of(new TrackrUser(0L, null, "mcTesterson", "tester00", "myCoolPassword",
						"testEmail@email.com")),
				Arguments.of(new TrackrUser(0L, "testy", null, "tester01", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(
						new TrackrUser(0L, "testy", "mcTesterson", null, "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new TrackrUser(0L, "testy", "mcTesterson", "tester02", null, "testEmail@email.com")),
				Arguments.of(new TrackrUser(0L, "testy", "mcTesterson", "tester03", "myCoolPassword", null)),
				Arguments.of(new TrackrUser(0L, "", "", "", "", "")),
				Arguments.of(
						new TrackrUser(0L, "", "mcTesterson", "tester04", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new TrackrUser(0L, "testy", "", "tester05", "myCoolPassword", "testEmail@email.com")),
				Arguments
						.of(new TrackrUser(0L, "testy", "mcTesterson", "", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new TrackrUser(0L, "testy", "mcTesterson", "tester06", "", "testEmail@email.com")),
				Arguments.of(new TrackrUser(0L, "testy", "mcTesterson", "tester07", "myCoolPassword", "")),

				// length tests
				Arguments.of(new TrackrUser(0L,
						"testyasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadfasdfasdfsadfasdfasdf",
						"mcTesterson", "tester08", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new TrackrUser(0L, "testy",
						"mcTestersonasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadf",
						"tester09", "myCoolPassword", ")#(*$)@(##@()*")),
				Arguments.of(new TrackrUser(0L, "testy", "mcTesterson",
						"tester00asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf",
						"myCoolPassword", "testEmail@email.com")),
				Arguments.of(new TrackrUser(0L, "testy", "mcTesterson", "tester10",
						"myCoolPasswordasdfasdfasdfasdfasdfasdfasdfasdfkljglkashvpawdvkjbaskjvhpweifpoiewnfshdvuashdpvoijasdkvnpas988hvjoaiwjegdafsdfasi2bgpviasjlvdjnlwkbfsjdbaoidngkksjadbhp9asdjoasdgasdfasdfashdjkshkvajhskljdvhlaskdjfhlkahjdsf",
						"testEmail@email.com")),
				Arguments.of(new TrackrUser(0L, "testy", "mcTesterson", "tester11", "myCoolPassword",
						"testEasdfasdfasdfasdfasdfasdfasdfasdfasdfsadfmail@emasdfasdfasdfasasdfasdfsdfasdfasdfasdfasdfdfasdfasail.com")),

				// bad email format
				Arguments.of(new TrackrUser(0L, "testy", "mcTesterson", "tester12", "myCoolPassword",
						"#@$@#$!@$!@*^&*!@^#"))

		);

	}
}
