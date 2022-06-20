package edu.bu.metcs673.trackr.transaction;

import edu.bu.metcs673.trackr.bankaccount.BankAccount;
import edu.bu.metcs673.trackr.common.TrackrInputValidationException;
import edu.bu.metcs673.trackr.user.TrackrUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Used to test methods in the TransactionServiceImpl class. Uses Mockito to
 * fake calls to the database so that business logic can be tested.
 *
 * @author Xiaobing Hou
 * @date 05/25/2022
 */
@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private TransactionServiceImpl transactionServiceImpl;

	public static final TrackrUser TEST_USER = new TrackrUser(1L, "Test", "Tester", "testTest", "blahblah",
			"test@email.com");
	public static final TrackrUser OTHER_USER = new TrackrUser(14L, "Test14", "Tester14", "testTest14", "blahblah14",
			"test14@email.com");
	public static final String TEST_DESCRIPTION = "TEST TRANSACTION";
	public static final double TEST_MONEY = 12.93;
	public static final String TEST_COUNTERPARTY = "WA";
	public static final String TEST_TIME = "05/24";
	public static final BankAccount TEST_BANK_ACCOUNT = new BankAccount(1L, TEST_USER,
			BankAccount.ACCOUNT_TYPE.CHECKING, "ACCOUNT_DESC", 100.0, BankAccount.ACCOUNT_STATUS.ACTIVE, null);
	public static TransactionDTO TEST_TRANSACTIONDTO = new TransactionDTO(1L, TEST_MONEY, TEST_TIME,
			TEST_COUNTERPARTY, TEST_DESCRIPTION);

	public static TransactionDTO OTHER_TRANSACTIONDTO = new TransactionDTO(2L, 500.0, TEST_TIME,
			TEST_COUNTERPARTY, TEST_DESCRIPTION);

	public static final BankAccount OTHER_BANK_ACCOUNT = new BankAccount(2L,OTHER_USER,
			BankAccount.ACCOUNT_TYPE.CHECKING, "ACCOUNT_DESC2", 50.0, BankAccount.ACCOUNT_STATUS.ACTIVE, null);

	@Test
	public void createTransactionTest() {

		Transaction mockTransaction = new Transaction(TEST_TRANSACTIONDTO, TEST_BANK_ACCOUNT);

		Mockito.when(transactionRepository.save(mockTransaction)).thenReturn(mockTransaction);

		Transaction transaction = transactionServiceImpl.createTransaction(TEST_TRANSACTIONDTO, TEST_BANK_ACCOUNT);

		assertEquals(mockTransaction, transaction);
	}

	@Test
	public void findTraByIdAndBankAccountIdTest_success() {
		Transaction mockTransaction = new Transaction(TEST_TRANSACTIONDTO, TEST_BANK_ACCOUNT);

		Mockito.when(transactionRepository.findTraByIdAndBankAccountId(1L, 1L)).thenReturn(mockTransaction);
		Transaction transaction = transactionServiceImpl.findTraByIdAndBankAccountId(1L, 1L);

		assertEquals(mockTransaction, transaction);

	}

	@Test
	public void findTraByIdAndBankAccountIdTest_failure() {
		Mockito.when(transactionRepository.findTraByIdAndBankAccountId(1L,1L))
				.thenReturn(null);
		assertThrows(TrackrInputValidationException.class,
				() -> transactionServiceImpl.findTraByIdAndBankAccountId(1L,1L));
	}

	@Test
	public void modifyTransactionTest() {
		Transaction mockTransaction1 = new Transaction(TEST_TRANSACTIONDTO, TEST_BANK_ACCOUNT);

		TEST_TRANSACTIONDTO.setMoney(1000.00);

		Mockito.when(transactionRepository.save(mockTransaction1)).thenReturn(mockTransaction1);

		Transaction transaction = transactionServiceImpl.modifyTransaction(mockTransaction1, TEST_TRANSACTIONDTO);

		assertEquals(mockTransaction1, transaction);
		assertEquals(1000, transaction.getMoney());
		assertEquals(1000, mockTransaction1.getMoney());

	}

	@Test
	public void modifyTransactionTest_failure() {
		Transaction mockTransaction = new Transaction(OTHER_TRANSACTIONDTO,OTHER_BANK_ACCOUNT);

		Mockito.when(transactionRepository.save(mockTransaction)).thenThrow(TrackrInputValidationException.class);
		assertThrows(TrackrInputValidationException.class,
				() -> transactionServiceImpl.modifyTransaction(mockTransaction,OTHER_TRANSACTIONDTO));
	}


	@Test
	public void deleteTransaction() {
		Transaction mockTransaction = new Transaction(TEST_TRANSACTIONDTO, TEST_BANK_ACCOUNT);

		Mockito.when(transactionRepository.save(mockTransaction)).thenReturn(mockTransaction);

		Transaction transaction = transactionServiceImpl.deleteTransaction(mockTransaction);

		assertEquals(Transaction.TRANSACTION_STATUS.INVALID, transaction.getStatus());
		assertEquals(Transaction.TRANSACTION_STATUS.INVALID, mockTransaction.getStatus());

	}

	@Test
	public void deleteTransaction_failure() {
		Transaction mockTransaction = new Transaction(4L,TEST_BANK_ACCOUNT,TEST_MONEY,TEST_TIME,TEST_COUNTERPARTY,TEST_DESCRIPTION, Transaction.TRANSACTION_STATUS.VALID);
		Mockito.when(transactionRepository.save(mockTransaction)).thenThrow(TrackrInputValidationException.class);
		assertThrows(TrackrInputValidationException.class, () ->transactionServiceImpl.deleteTransaction(mockTransaction));
	}
}
