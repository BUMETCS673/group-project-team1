package edu.bu.metcs673.trackr.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.bu.metcs673.trackr.common.TrackrInputValidationException;
import edu.bu.metcs673.trackr.domain.User;

@SpringBootTest
public class UserServiceImplTest {

	@Autowired
	private UserServiceImpl serviceImpl;

	@ParameterizedTest
	@MethodSource("generateTestUsers")
	public void testUserInputValidations(User testUser) {

		assertThrows(TrackrInputValidationException.class, () -> serviceImpl.validateParameters(testUser));
	}

	/**
	 * Creates different objects to be used in the parameterized test. Used to test
	 * the 'validate' method.
	 * 
	 * @return
	 */
	private static Stream<Arguments> generateTestUsers() {
		return Stream.of(Arguments.of(User.builder().username(null).password(null).email(null).build()),
				Arguments.of(User.builder().username("").password("").email("").build()),
				Arguments.of(User.builder().username("tflucker").password("blah").email("testEmail@email.com").build()),
				Arguments.of(User.builder().username("tflucker").password("blah").email(")#(*$)@(##@()*").build()));
	}

}
