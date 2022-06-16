package edu.bu.metcs673.trackr.transaction;

import java.util.List;

import org.springframework.stereotype.Component;

import edu.bu.metcs673.trackr.bankaccount.BankAccount;

/**
 * Interface which defines methods which will be implemented in the
 * "TransactionServiceImpl". This interface is reusable, so other classes could
 * extend this if they wanted / needed to.
 *
 * @author Xiaobing Hou
 * @date 05/23/2022
 */
@Component
public interface TransactionService {

	/**
	 * The purpose of this method is to create a transaction in a bank account by
	 * 'transactionInput' and 'bankAccount' value.
	 *
	 * @param transactionInput this is a TransactionDTO object which gotten from the
	 *                         request body
	 * @param bankAccount      this is a bank account object which you will insert a
	 *                         transaction into
	 * @return Transaction
	 * @author Xiaobing Hou
	 * @date 05/23/2022
	 */
	public Transaction createTransaction(TransactionDTO transactionInput, BankAccount bankAccount);

	/**
	 * The purpose of this method is to find a special transaction by
	 * 'transactionId' and 'bankAccountId' value.
	 *
	 * @param transactionId this is transaction id
	 * @param bankAccountId This is the id of the corresponding bank account
	 * @return Transaction
	 * @author Xiaobing Hou
	 * @date 05/23/2022
	 */
	public Transaction findTraByIdAndBankAccountId(long transactionId, long bankAccountId);

	/**
	 * The purpose of this method is to modify a special transaction record by
	 * 'transaction' and 'transactionInput' value.
	 *
	 * @param transaction      this is a Transaction object
	 * @param transactionInput This is a TransactionDTO object
	 * @date 05/23/2022
	 */
	public Transaction modifyTransaction(Transaction transaction, TransactionDTO transactionInput);

	/**
	 * The purpose of this method is to find all the transaction of a bank account
	 *
	 * @param bankAccountId this is bank account id
	 * @return Transaction
	 * @author Xiaobing Hou
	 * @date 05/23/2022
	 */
	public List<Transaction> findAllTraByBankAccountId(long bankAccountId);

	/**
	 * The purpose of this method is to invalid a transaction
	 *
	 * @param transaction this is a Transaction object
	 * @return Transaction
	 * @author Xiaobing Hou
	 * @date 05/25/2022
	 */
	public Transaction deleteTransaction(Transaction transaction);

	/**
	 * Gets all transactions associated to the current user.
	 * @param userId 
	 * 
	 * @return
	 */
	public List<Transaction> findAllTransactions(long userId);
}
