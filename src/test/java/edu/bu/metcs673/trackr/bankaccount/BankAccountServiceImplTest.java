package edu.bu.metcs673.trackr.bankaccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.bu.metcs673.trackr.bankaccount.BankAccount.ACCOUNT_TYPE;
import edu.bu.metcs673.trackr.common.TrackrInputValidationException;
import edu.bu.metcs673.trackr.user.TrackrUser;

/**
 * Used to test methods in the BankAccountServiceImpl class. Uses Mockito to
 * fake calls to the database so that business logic can be tested.
 * 
 * @author Tim Flucker
 *
 */
@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplTest {

	@Mock
	private BankAccountRepository accountRepository;

	@InjectMocks
	private BankAccountServiceImpl accountServiceImpl;

	public static final String TEST_DESCRIPTION = "TEST BANK ACCOUNT";
	public static final String TEST_DESCRIPTION_OTHER = "MY SUPER SECRET ACCOUNT";
	public static final double TEST_BALANCE = 12.93;
	public static final double TEST_BALANCE_OTHER = 12411233.72;

	public static final TrackrUser TEST_USER = new TrackrUser(0L, "Test", "Tester", "testTest", "blahblah",
			"test@email.com");
	public static final TrackrUser OTHER_USER = new TrackrUser(14L, "Test14", "Tester14", "testTest14", "blahblah14",
			"test14@email.com");

	@Test
	public void createBankAccountTest() {

		BankAccount mockAccount = new BankAccount(0L, TEST_USER, BankAccount.ACCOUNT_TYPE.CHECKING, TEST_DESCRIPTION,
				TEST_BALANCE, BankAccount.ACCOUNT_STATUS.ACTIVE, null);
		BankAccountDTO bankAcctDTO = new BankAccountDTO(BankAccount.ACCOUNT_TYPE.CHECKING, TEST_DESCRIPTION,
				TEST_BALANCE);

		Mockito.when(accountRepository.save(mockAccount)).thenReturn(mockAccount);

		BankAccount acct = accountServiceImpl.createBankAccount(bankAcctDTO, TEST_USER);

		assertEquals(mockAccount, acct);
	}

	@Test
	public void modifyBankAccount_success() {
		BankAccount mockAccount = new BankAccount(0L, TEST_USER, BankAccount.ACCOUNT_TYPE.CHECKING, TEST_DESCRIPTION,
				TEST_BALANCE, BankAccount.ACCOUNT_STATUS.ACTIVE, null);
		BankAccount modifiedAccount = new BankAccount(0L, TEST_USER, BankAccount.ACCOUNT_TYPE.SAVING,
				TEST_DESCRIPTION_OTHER, TEST_BALANCE_OTHER, BankAccount.ACCOUNT_STATUS.ACTIVE, null);
		BankAccountDTO bankAcctDTO = new BankAccountDTO(BankAccount.ACCOUNT_TYPE.SAVING, TEST_DESCRIPTION_OTHER,
				TEST_BALANCE_OTHER);

		Mockito.when(accountRepository.findById(0L)).thenReturn(Optional.of(mockAccount));

		Mockito.when(accountRepository.save(modifiedAccount)).thenReturn(modifiedAccount);

		BankAccount updatedAccount = accountServiceImpl.modifyBankAccount(bankAcctDTO, TEST_USER.getId(), 0L);

		assertNotNull(updatedAccount);
		assertEquals(ACCOUNT_TYPE.SAVING, updatedAccount.getAccountType());
		assertEquals(TEST_DESCRIPTION_OTHER, updatedAccount.getAccountDescription());
		assertEquals(TEST_BALANCE_OTHER, updatedAccount.getBalance());

	}

	@Test
	public void modifyBankAccount_failure() {
		BankAccount mockAccount = new BankAccount(0L, OTHER_USER, BankAccount.ACCOUNT_TYPE.CHECKING, TEST_DESCRIPTION,
				TEST_BALANCE, BankAccount.ACCOUNT_STATUS.ACTIVE, null);
		BankAccountDTO bankAcctDTO = new BankAccountDTO(BankAccount.ACCOUNT_TYPE.SAVING, TEST_DESCRIPTION_OTHER,
				TEST_BALANCE_OTHER);

		Mockito.when(accountRepository.findById(0L)).thenReturn(Optional.of(mockAccount));

		assertThrows(TrackrInputValidationException.class,
				() -> accountServiceImpl.modifyBankAccount(bankAcctDTO, TEST_USER.getId(), 0L));
	}

	// no test for deactivateBankAccount_success because method returns void

	@Test
	public void deactivateBankAccount_failure() {
		BankAccount mockAccount = new BankAccount(0L, OTHER_USER, BankAccount.ACCOUNT_TYPE.CHECKING, TEST_DESCRIPTION,
				TEST_BALANCE, BankAccount.ACCOUNT_STATUS.ACTIVE, null);

		Mockito.when(accountRepository.findById(0L)).thenReturn(Optional.of(mockAccount));

		assertThrows(TrackrInputValidationException.class,
				() -> accountServiceImpl.deactivateBankAccount(TEST_USER.getId(), 0L));
	}

	@Test
	public void findBankAccountByUserIdTest_success() {
		BankAccount mockAccount = new BankAccount(0L, TEST_USER, BankAccount.ACCOUNT_TYPE.CHECKING, TEST_DESCRIPTION,
				TEST_BALANCE, BankAccount.ACCOUNT_STATUS.ACTIVE, null);

		Mockito.when(accountRepository.findByIdAndUserIdAndStatus(0L, 0L, BankAccount.ACCOUNT_STATUS.ACTIVE))
				.thenReturn(mockAccount);
		BankAccount bankAccount = accountServiceImpl.findBankAccountByIdAndUserId(0L, 0L);
		assertEquals(mockAccount, bankAccount);

	}

	@Test
	public void findBankAccountByUserIdTest_failure() {
		Mockito.when(accountRepository.findByIdAndUserIdAndStatus(0L, 0L, BankAccount.ACCOUNT_STATUS.ACTIVE))
				.thenReturn(null);
		assertThrows(TrackrInputValidationException.class,
				() -> accountServiceImpl.findBankAccountByIdAndUserId(0L, 0L));
	}
}
