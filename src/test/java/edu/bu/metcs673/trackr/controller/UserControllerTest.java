package edu.bu.metcs673.trackr.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.bu.metcs673.trackr.domain.User;

@SpringBootTest
public class UserControllerTest {

	@Autowired
	UserController controller = new UserController();

	@ParameterizedTest
	@MethodSource("generateTestUsers")
	public void userAnnotationValidationTest(User testUser) {

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
				new User()),
				Arguments.of(new User(0L, null, "mcTesterson", "tester00", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new User(0L, "testy", null, "tester00", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new User(0L, "testy", "mcTesterson", null, "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new User(0L, "testy", "mcTesterson", "tester00", null, "testEmail@email.com")),
				Arguments.of(new User(0L, "testy", "mcTesterson", "tester00", "myCoolPassword", null)),
				Arguments.of(new User(0L, "", "", "", "", "")),
				Arguments.of(new User(0L, "", "mcTesterson", "tester00", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new User(0L, "testy", "", "tester00", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new User(0L, "testy", "mcTesterson", "", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new User(0L, "testy", "mcTesterson", "tester00", "", "testEmail@email.com")),
				Arguments.of(new User(0L, "testy", "mcTesterson", "tester00", "myCoolPassword", "")),

				// length tests
				Arguments.of(new User(0L,
						"testyasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadfasdfasdfsadfasdfasdf",
						"mcTesterson", "tester00", "myCoolPassword", "testEmail@email.com")),
				Arguments.of(new User(0L, "testy",
						"mcTestersonasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadf",
						"tester00", "myCoolPassword", ")#(*$)@(##@()*")),
				Arguments.of(new User(0L, "testy", "mcTesterson",
						"tester00asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf",
						"myCoolPassword", "testEmail@email.com")),
				Arguments.of(new User(0L, "testy", "mcTesterson", "tester00",
						"myCoolPasswordasdfasdfasdfasdfasdfasdfasdfasdf", "testEmail@email.com")),
				Arguments.of(new User(0L, "testy", "mcTesterson", "tester00", "myCoolPassword",
						"testEasdfasdfasdfasdfasdfasdfasdfasdfasdfsadfmail@emasdfasdfasdfasasdfasdfsdfasdfasdfasdfasdfdfasdfasail.com")),

				// bad email format
				Arguments.of(new User(0L, "testy", "mcTesterson", "tester00", "myCoolPassword", "#@$@#$!@$!@*^&*!@^#"))

		);

	}
}
