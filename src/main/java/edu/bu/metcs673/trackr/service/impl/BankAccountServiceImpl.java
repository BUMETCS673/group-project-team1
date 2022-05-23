package edu.bu.metcs673.trackr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.bu.metcs673.trackr.api.BankAccountDTO;
import edu.bu.metcs673.trackr.domain.BankAccount;
import edu.bu.metcs673.trackr.domain.TrackrUser;
import edu.bu.metcs673.trackr.repo.BankAccountRepository;
import edu.bu.metcs673.trackr.service.BankAccountService;

@Service
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Override
	public BankAccount createBankAccount(BankAccountDTO bankAccountInput, TrackrUser user) {

		// create new bank account object using DTO fields, set user and accountStatus as well
		BankAccount bankAccount = new BankAccount(0L, user, bankAccountInput.getAccountType(),
				bankAccountInput.getAccountDescription(), bankAccountInput.getBalance(),
				BankAccount.ACCOUNT_STATUS.ACTIVE);

		return bankAccountRepository.save(bankAccount);
	}

}
