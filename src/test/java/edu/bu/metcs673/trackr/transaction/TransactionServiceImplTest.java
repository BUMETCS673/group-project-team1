package edu.bu.metcs673.trackr.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.bu.metcs673.trackr.bankaccount.BankAccount;
import edu.bu.metcs673.trackr.transaction.Transaction;
import edu.bu.metcs673.trackr.transaction.TransactionDTO;
import edu.bu.metcs673.trackr.transaction.TransactionRepository;
import edu.bu.metcs673.trackr.transaction.TransactionServiceImpl;
import edu.bu.metcs673.trackr.user.TrackrUser;

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
	public static final String TEST_DESCRIPTION = "TEST TRANSACTION";
	public static final double TEST_MONEY = 12.93;
	public static final String TEST_COUNTERPARTY = "WA";
	public static final String TEST_TIME = "05/24";
	public static final BankAccount TEST_BANK_ACCOUNT = new BankAccount(1L, TEST_USER,
			BankAccount.ACCOUNT_TYPE.CHECKING, "ACCOUNT_DESC", 100.0, BankAccount.ACCOUNT_STATUS.ACTIVE);
	public static final TransactionDTO TEST_TRANSACTIONDTO = new TransactionDTO(1L, TEST_MONEY, TEST_TIME,
			TEST_COUNTERPARTY, TEST_DESCRIPTION);

	@Test
	public void createTransactionTest() {

		Transaction mockTransaction = new Transaction(TEST_TRANSACTIONDTO, TEST_BANK_ACCOUNT);

		Mockito.when(transactionRepository.save(mockTransaction)).thenReturn(mockTransaction);

		Transaction transaction = transactionServiceImpl.createTransaction(TEST_TRANSACTIONDTO, TEST_BANK_ACCOUNT);

		assertEquals(mockTransaction, transaction);
	}

	@Test
	public void findTraByIdAndBankAccountIdTest() {
		Transaction mockTransaction = new Transaction(TEST_TRANSACTIONDTO, TEST_BANK_ACCOUNT);

		Mockito.when(transactionRepository.findTraByIdAndBankAccountId(1L, 1L)).thenReturn(mockTransaction);
		Transaction transaction = transactionServiceImpl.findTraByIdAndBankAccountId(1L, 1L);

		assertEquals(mockTransaction, transaction);

	}

	@Test
	public void modifyTransactionTest() {
		Transaction mockTransaction1 = new Transaction(TEST_TRANSACTIONDTO, TEST_BANK_ACCOUNT);

		TEST_TRANSACTIONDTO.setMoney(1000);

		Mockito.when(transactionRepository.save(mockTransaction1)).thenReturn(mockTransaction1);

		Transaction transaction = transactionServiceImpl.modifyTransaction(mockTransaction1, TEST_TRANSACTIONDTO);

		assertEquals(mockTransaction1, transaction);
		assertEquals(1000, transaction.getMoney());
		assertEquals(1000, mockTransaction1.getMoney());

	}

	@Test
	public void deleteTransaction() {
		Transaction mockTransaction = new Transaction(TEST_TRANSACTIONDTO, TEST_BANK_ACCOUNT);

		Mockito.when(transactionRepository.save(mockTransaction)).thenReturn(mockTransaction);

		Transaction transaction = transactionServiceImpl.deleteTransaction(mockTransaction);

		assertEquals(Transaction.TRANSACTION_STATUS.INVALID, transaction.getStatus());
		assertEquals(Transaction.TRANSACTION_STATUS.INVALID, mockTransaction.getStatus());

	}

}
