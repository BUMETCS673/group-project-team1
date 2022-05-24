package edu.bu.metcs673.trackr.service;

import org.springframework.stereotype.Component;

import edu.bu.metcs673.trackr.api.BankAccountDTO;
import edu.bu.metcs673.trackr.domain.BankAccount;
import edu.bu.metcs673.trackr.domain.TrackrUser;

@Component
public interface BankAccountService {

	/**
	 * Creates a new bank account using the provided object, associate to the user
	 * making the request. Fields not provided by the user will be filled in with
	 * their default values.
	 * 
	 * @param bankAccountInput
	 * @return
	 */
	public BankAccount createBankAccount(BankAccountDTO bankAccountInput, TrackrUser user);
}