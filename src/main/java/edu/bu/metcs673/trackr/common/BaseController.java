package edu.bu.metcs673.trackr.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.bu.metcs673.trackr.bankaccount.BankAccount;
import edu.bu.metcs673.trackr.bankaccount.BankAccountService;
import edu.bu.metcs673.trackr.user.TrackrUser;
import edu.bu.metcs673.trackr.user.TrackrUserService;

@Component
public abstract class BaseController {

	@Autowired
	private TrackrUserService trackrUserService;

	@Autowired
	private BankAccountService bankAccountService;

	/**
	 * The purpose of this method is to get the current user
	 *
	 * @return TrackrUser
	 * @author Xiaobing Hou
	 * @date 06/01/2022
	 */
	public TrackrUser getUser() {
		return trackrUserService.getCurrentUser();
	}

	/**
	 * The purpose of this method is to get a bank account by 'bankAccountId' value
	 *
	 * @param bankAccountId this is bank account id
	 * @return BankAccount
	 * @author Xiaobing Hou
	 * @date 05/25/2022
	 */
	public BankAccount getBankAccount(long bankAccountId) {
		return bankAccountService.findBankAccountByIdAndUserId(bankAccountId, getUser().getId());
	}
}
