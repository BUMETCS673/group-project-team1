package edu.bu.metcs673.trackr.bankaccount;

import org.springframework.stereotype.Component;

import edu.bu.metcs673.trackr.user.TrackrUser;

import java.util.List;

/**
 * Interface which defines methods which will be implemented in the
 * "BankAccountServiceImpl". This interface is reusable, so other classes could
 * extend this if they wanted / needed to.
 *
 * @author Tim Flucker
 */
@Component
public interface BankAccountService {

	/**
	 * Creates a new bank account using the provided object, associate to the user
	 * making the request. Fields not provided by the user will be filled in with
	 * their default values.
	 *
	 * @param bankAccountInput
	 * @return BankAccount
	 */
	public BankAccount createBankAccount(BankAccountDTO bankAccountInput, TrackrUser user);

	/**
	 * Modifies a BankAccount record with the provided id, if the provide TrackrUser
	 * is the same one who created the record. Value in the BankAccountDTO will
	 * overwrite existing values.
	 *
	 * @param bankAccountInput
	 * @param user
	 * @param id
	 * @return BankAccount
	 */
	public BankAccount modifyBankAccount(BankAccountDTO bankAccountInput, long userId, long id);

	/**
	 * Updates a status in a BankAccount record with the provided id, if the
	 * provided TrackrUser is the same associated to the record. Sets
	 * 'accountStatus' to INACTIVE which prevents new transactions from being
	 * associated to the BankAccount record.
	 *
	 * @param userId
	 * @param id
	 * @return
	 */
	public void deactivateBankAccount(long userId, long id);

	/**
	 * The purpose of this method is to find a special bank account by 'id' and
	 * 'userId' value.
	 *
	 * @param bankAccountId this is bank account id
	 * @param userId        this is user id
	 * @return BankAccount
	 * @author Xiaobing Hou
	 */
	public BankAccount findBankAccountByIdAndUserId(long bankAccountId, long userId);

	/**
	 * Returns a list of Bank Account records associated to the provided userId.
	 * 
	 * @param userId
	 * @return List of BankAccount objects
	 */
	public List<BankAccount> findBankAccountsByUserId(long userId);
}
