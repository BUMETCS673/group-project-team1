package edu.bu.metcs673.trackr.transaction;

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

import edu.bu.metcs673.trackr.bankaccount.BankAccount;
import edu.bu.metcs673.trackr.user.TrackrUser;

/**
 * Used to test methods in the Transaction class.
 *
 * @author Xiaobing Hou
 * @date 05/25/2022
 */
public class TransactionTest {
	public static final TrackrUser TEST_USER = new TrackrUser(1L, "Test", "Tester", "testTest", "blahblah",
			"test@email.com");
	public static final String TEST_DESCRIPTION = "TEST TRANSACTION";
	public static final double TEST_MONEY = 12.93;
	public static final String TEST_COUNTERPARTY = "WA";
	public static final String TEST_TIME = "05/24";
	public static final BankAccount TEST_BANK_ACCOUNT = new BankAccount(1L, TEST_USER,
			BankAccount.ACCOUNT_TYPE.CHECKING, "ACCOUNT_DESC", 100.0, BankAccount.ACCOUNT_STATUS.ACTIVE, null);

	public static final TransactionDTO TEST_TRANSACTIONDTO = new TransactionDTO(1L, TEST_MONEY, TEST_TIME,
			TEST_COUNTERPARTY, TEST_DESCRIPTION);

	private static Validator validator;

	@BeforeAll
	public static void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void getterSettersTest() {
		Transaction transaction = new Transaction();

		assertNull(transaction.getTransactionDescription());
		assertNull(transaction.getStatus());
		assertNull(transaction.getCounterparty());
		assertNull(transaction.getBankAccount());
		assertNull(transaction.getTime());

		assertEquals(0, transaction.getMoney());

		transaction.setTransactionDescription(TEST_DESCRIPTION);
		transaction.setStatus(Transaction.TRANSACTION_STATUS.INVALID);
		transaction.setCounterparty(TEST_COUNTERPARTY);
		transaction.setBankAccount(TEST_BANK_ACCOUNT);
		transaction.setTime(TEST_TIME);
		transaction.setMoney(TEST_MONEY);

		assertEquals(TEST_DESCRIPTION, transaction.getTransactionDescription());
		assertEquals(Transaction.TRANSACTION_STATUS.INVALID, transaction.getStatus());
		assertEquals(TEST_COUNTERPARTY, transaction.getCounterparty());
		assertEquals(TEST_BANK_ACCOUNT, transaction.getBankAccount());
		assertEquals(TEST_TIME, transaction.getTime());
		assertEquals(TEST_MONEY, transaction.getMoney());

	}

	@Test
	public void constructorTest() {
		Transaction transaction = new Transaction(TEST_TRANSACTIONDTO, TEST_BANK_ACCOUNT);

		assertEquals(TEST_DESCRIPTION, transaction.getTransactionDescription());
		assertEquals(Transaction.TRANSACTION_STATUS.VALID, transaction.getStatus());
		assertEquals(TEST_TIME, transaction.getTime());
		assertEquals(TEST_MONEY, transaction.getMoney());
		assertEquals(TEST_COUNTERPARTY, transaction.getCounterparty());
		assertEquals(TEST_BANK_ACCOUNT, transaction.getBankAccount());
	}

	@ParameterizedTest
	@MethodSource("generateTestData")
	public void testAnnotationBasedValidations(TransactionDTO dto) {

		Set<ConstraintViolation<TransactionDTO>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty());
	}

	private static Stream<Arguments> generateTestData() {
		return Stream.of(Arguments.of(new TransactionDTO()),
				Arguments.of(new TransactionDTO(null, TEST_MONEY, TEST_TIME, TEST_COUNTERPARTY, TEST_DESCRIPTION)),
				Arguments.of(new TransactionDTO(1L, null, TEST_TIME, TEST_COUNTERPARTY, TEST_DESCRIPTION)),
				Arguments.of(new TransactionDTO(1L, TEST_MONEY, null, TEST_COUNTERPARTY, TEST_DESCRIPTION)),
				Arguments.of(new TransactionDTO(1L, TEST_MONEY, TEST_TIME, null, TEST_DESCRIPTION)),
				Arguments.of(new TransactionDTO(1L, TEST_MONEY, TEST_TIME,
						"kjflkasjdflkjasfhasdhfuiahwoeibwviaushoiuahsdiuawnekjbasivuhaioudvhajksdhjklashdfiouaasdfasdfahsdifuhaskjdfh",
						TEST_DESCRIPTION)),
				Arguments.of(new TransactionDTO(1L, TEST_MONEY, TEST_TIME, TEST_COUNTERPARTY,
						"kjflkasjdflkjasfhasdhfuiahwoeibwviaushoiuahsdiuawnekjbasivuhaioudvhajksdhjklashdfiouaasdfasdfahsdifuhaskjdfhkjflkasjdflkjasfhas"
								+ "dhfuiahwoeibwviaushoiuahsdiuawnekjbasivuhaioudvhajksdhjklashdfiouaasdfasdfahsdifuhaskjdfahdsflkjashdflkjahsdlkfjhalskjdhfhasdfasdfasdfasdf")));
	}

}
