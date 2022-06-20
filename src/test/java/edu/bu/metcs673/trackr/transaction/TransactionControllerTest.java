package edu.bu.metcs673.trackr.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.bankaccount.BankAccount;
import edu.bu.metcs673.trackr.bankaccount.BankAccountService;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.transaction.Transaction.TRANSACTION_STATUS;
import edu.bu.metcs673.trackr.user.TrackrUser;
import edu.bu.metcs673.trackr.user.TrackrUserService;

/**
 * Used to test methods in the TransactionController class. Uses Mockito to fake
 * calls to the database so that business logic can be tested.
 *
 * @author Xiaobing Hou
 * @date 05/25/2022
 */
@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

	public static final TrackrUser TEST_USER = new TrackrUser(1L, "test", "User", "testUser", "blahblah",
			"test@email.com");

	public static final BankAccount.ACCOUNT_TYPE TEST_TYPE = BankAccount.ACCOUNT_TYPE.CHECKING;
	public static final String ACCOUNT_DESC = "TEST DESCRIPTION";
	public static final double TEST_BALANCE = 500.56;
	public static final double MONEY = 100.0;
	public static final String TIME = "05/25/2022";
	public static final String COUNTERPARTY = "WA";
	public static final String TRANSACTION_DESC = "TEST DESCRIPTION";

	public static final BankAccount TEST_BANK_ACCOUNT = new BankAccount(1L, TEST_USER, TEST_TYPE, ACCOUNT_DESC,
			TEST_BALANCE, BankAccount.ACCOUNT_STATUS.ACTIVE, null);

	@Mock
	private TransactionService transactionService;

	@Mock
	private BankAccountService bankAccountService;

	@Mock
	private TrackrUserService userService;

	@InjectMocks
	private TransactionController transactionController;

	@Test
	public void testAnnotationValidations() {

		TransactionDTO transactionDTO = new TransactionDTO(1L, MONEY, TIME, COUNTERPARTY, TRANSACTION_DESC);

		Transaction mockTransaction = new Transaction(transactionDTO, TEST_BANK_ACCOUNT);

		List<BankAccount> acctList = new ArrayList<BankAccount>();
		acctList.add(TEST_BANK_ACCOUNT);

		// define mock responses
		Mockito.when(userService.getCurrentUser()).thenReturn(TEST_USER);
		Mockito.when(bankAccountService.findBankAccountByIdAndUserId(1L, 1L)).thenReturn(TEST_BANK_ACCOUNT);
		Mockito.when(bankAccountService.findBankAccountsByUserId(1L)).thenReturn(acctList);
		Mockito.when(transactionService.createTransaction(transactionDTO, TEST_BANK_ACCOUNT))
				.thenReturn(mockTransaction);

		ResponseEntity<GenericApiResponse<Transaction>> response = transactionController
				.createTransaction(transactionDTO);

		ResponseEntity<GenericApiResponse<Transaction>> mockResponse = new ResponseEntity<>(
				GenericApiResponse.successResponse(MessageFormat.format(CommonConstants.CREATE_TRANSACTION,
						String.valueOf(mockTransaction.getId())), mockTransaction),
				HttpStatus.OK);

		assertEquals(mockResponse, response);

		// Test for modify
		TransactionDTO transactionDTO_1 = new TransactionDTO(1L, 200.0, TIME, COUNTERPARTY, TRANSACTION_DESC);
		Transaction mockTransaction_1 = new Transaction(transactionDTO_1, TEST_BANK_ACCOUNT);
		mockTransaction_1.setId(1L);

		Mockito.when(transactionService.findTraByIdAndBankAccountId(1L, 1L)).thenReturn(mockTransaction);
		Mockito.when(transactionService.modifyTransaction(mockTransaction, transactionDTO_1))
				.thenReturn(mockTransaction_1);

		ResponseEntity<GenericApiResponse<Transaction>> response_1 = transactionController
				.modifyTransaction(transactionDTO_1.getBankAccountId(), transactionDTO_1);
		ResponseEntity<GenericApiResponse<Transaction>> mockResponse_1 = new ResponseEntity<>(
				GenericApiResponse.successResponse(MessageFormat.format(CommonConstants.MODIFY_TRANSACTION,
						String.valueOf(mockTransaction_1.getId())), mockTransaction_1),
				HttpStatus.OK);

		assertEquals(mockResponse_1, response_1);

	}

	@Test
	public void findAllTransactionsTest() {

		Transaction transaction1 = new Transaction(1L, TEST_BANK_ACCOUNT, MONEY, TIME, COUNTERPARTY, TRANSACTION_DESC,
				TRANSACTION_STATUS.VALID);
		Transaction transaction2 = new Transaction(2L, TEST_BANK_ACCOUNT, MONEY, TIME, COUNTERPARTY, TRANSACTION_DESC,
				TRANSACTION_STATUS.VALID);
		Transaction transaction3 = new Transaction(3L, TEST_BANK_ACCOUNT, MONEY, TIME, COUNTERPARTY, TRANSACTION_DESC,
				TRANSACTION_STATUS.VALID);

		List<Transaction> transactionList = new ArrayList<>();
		transactionList.add(transaction1);
		transactionList.add(transaction2);
		transactionList.add(transaction3);

		Mockito.when(userService.getCurrentUser()).thenReturn(TEST_USER);
		Mockito.when(transactionService.findAllTransactions(1L)).thenReturn(transactionList);

		ResponseEntity<GenericApiResponse<List<Transaction>>> mockResponse = ResponseEntity
				.ok(GenericApiResponse.successResponse(
						MessageFormat.format(CommonConstants.FIND_ALL_TRANSACTION_FOR_USER, TEST_USER.getUsername()), transactionList));

		ResponseEntity<GenericApiResponse<List<Transaction>>> response = transactionController.findAllTransactions();
		
		assertEquals(mockResponse, response);
	}
}
