package edu.bu.metcs673.trackr.bankaccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

import edu.bu.metcs673.trackr.user.TrackrUser;

public class BankAccountTest {

	public static final String TEST_DESCRIPTION = "TEST BANK ACCOUNT";
	public static final double TEST_BALANCE = 12.93;
	public static final TrackrUser TEST_USER = new TrackrUser(0L, "Test", "Tester", "testTest", "blahblah",
			"test@email.com");

	public static final BankAccount.ACCOUNT_TYPE TEST_TYPE = BankAccount.ACCOUNT_TYPE.CHECKING;

	private static Validator validator;

	@BeforeAll
	public static void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void getterSettersTest() {
		BankAccount acct = new BankAccount();

		assertNull(acct.getAccountType());
		assertNull(acct.getAccountDescription());
		assertEquals(0, acct.getBalance());
		assertNull(acct.getUser());

		acct.setAccountDescription(TEST_DESCRIPTION);
		acct.setAccountType(BankAccount.ACCOUNT_TYPE.CHECKING);
		acct.setStatus(BankAccount.ACCOUNT_STATUS.ACTIVE);
		acct.setBalance(TEST_BALANCE);
		acct.setUser(TEST_USER);

		assertEquals(TEST_DESCRIPTION, acct.getAccountDescription());
		assertEquals(BankAccount.ACCOUNT_STATUS.ACTIVE, acct.getStatus());
		assertEquals(BankAccount.ACCOUNT_TYPE.CHECKING, acct.getAccountType());
		assertEquals(TEST_BALANCE, acct.getBalance());
		assertEquals(TEST_USER, acct.getUser());
	}

	@Test
	public void emptyConstructorTest() {
		BankAccount acct = new BankAccount();

		assertNull(acct.getAccountType());
		assertNull(acct.getAccountDescription());
		assertEquals(0, acct.getBalance());
		assertNull(acct.getUser());
	}

	@Test
	public void fullArgsConstructorTest() {
		BankAccount acct = new BankAccount(0L, TEST_USER, BankAccount.ACCOUNT_TYPE.CHECKING, TEST_DESCRIPTION,
				TEST_BALANCE, BankAccount.ACCOUNT_STATUS.ACTIVE, null);

		assertEquals(TEST_DESCRIPTION, acct.getAccountDescription());
		assertEquals(BankAccount.ACCOUNT_STATUS.ACTIVE, acct.getStatus());
		assertEquals(BankAccount.ACCOUNT_TYPE.CHECKING, acct.getAccountType());
		assertEquals(TEST_BALANCE, acct.getBalance());
		assertEquals(TEST_USER, acct.getUser());
	}

	@ParameterizedTest
	@MethodSource("generateTestData")
	public void testAnnotationBasedValidations(BankAccountDTO accountDTO) {

		Set<ConstraintViolation<BankAccountDTO>> violations = validator.validate(accountDTO);
		assertFalse(violations.isEmpty());
	}

	private static Stream<Arguments> generateTestData() {
		return Stream.of(Arguments.of(new BankAccountDTO()),
				Arguments.of(new BankAccountDTO(null, TEST_DESCRIPTION, TEST_BALANCE)),
				Arguments.of(new BankAccountDTO(TEST_TYPE, TEST_DESCRIPTION, null)),
				Arguments.of(new BankAccountDTO(TEST_TYPE, TEST_DESCRIPTION, TEST_BALANCE * -1)),
				Arguments.of(new BankAccountDTO(TEST_TYPE,
						IntStream.range(0, 256).boxed().map(Object::toString).collect(Collectors.joining()),
						TEST_BALANCE))

		);
	}
}
