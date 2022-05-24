package edu.bu.metcs673.trackr.service.impl;

import edu.bu.metcs673.trackr.api.BankAccountDTO;
import edu.bu.metcs673.trackr.domain.BankAccount;
import edu.bu.metcs673.trackr.domain.TrackrUser;
import edu.bu.metcs673.trackr.repo.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplTest {

	
	@Mock
	private BankAccountRepository accountRepository;
	
	@InjectMocks
	private BankAccountServiceImpl accountServiceImpl;
	
	public static final String TEST_DESCRIPTION = "TEST BANK ACCOUNT";
	public static final double TEST_BALANCE = 12.93;
	public static final TrackrUser TEST_USER = new TrackrUser(0L, "Test", "Tester", "testTest", "blahblah",
			"test@email.com");
	
	@Test
	public void createBankAccountTest() {
	
		BankAccount mockAccount = new BankAccount(0L, TEST_USER, BankAccount.ACCOUNT_TYPE.CHECKING, TEST_DESCRIPTION,
				TEST_BALANCE, BankAccount.ACCOUNT_STATUS.ACTIVE);
		BankAccountDTO bankAcctDTO = new BankAccountDTO(BankAccount.ACCOUNT_TYPE.CHECKING, TEST_DESCRIPTION,
				TEST_BALANCE);
		
		Mockito.when(accountRepository.save(mockAccount)).thenReturn(mockAccount);
		
		BankAccount acct = accountServiceImpl.createBankAccount(bankAcctDTO, TEST_USER);
		
		assertEquals(mockAccount, acct);
	}
	
	
}
