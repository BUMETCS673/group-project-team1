package edu.bu.metcs673.trackr.transaction;

import java.text.MessageFormat;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.bankaccount.BankAccount;
import edu.bu.metcs673.trackr.common.BaseController;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.common.TrackrInputValidationException;
import edu.bu.metcs673.trackr.user.TrackrUser;

/**
 * Controller for Transactions Management. Contains 'Create', 'Find', 'Modify',
 * and 'Delete' APIs
 *
 * @author Xiaobing Hou
 * @date 05/21/2022
 */
@Validated
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController extends BaseController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping
	public ResponseEntity<GenericApiResponse<List<Transaction>>> findAllTransactions() {
		TrackrUser user = getUser();
		List<Transaction> allTransactions = transactionService.findAllTransactions(user.getId());

		return ResponseEntity.ok(GenericApiResponse.successResponse(
				MessageFormat.format(CommonConstants.FIND_ALL_TRANSACTION_FOR_USER, user.getUsername()),
				allTransactions));
	}

	/**
	 * The purpose of this method is to find all transaction by 'bankAccountId'
	 * value
	 *
	 * @param bankAccountId this is bank account id
	 * @return ResponseEntity<GenericApiResponse<List<Transaction>>>
	 */
	@GetMapping("/{bankAccountId}")
	public ResponseEntity<GenericApiResponse<List<Transaction>>> findAllTransactionById(
			@PathVariable(value = "bankAccountId") long bankAccountId) {

		// verify that user is associated to the requested bank account
		if (isUserAssociatedToBankAccount(bankAccountId)) {
			// get all transaction related to this bankAccountId
			List<Transaction> transactions = transactionService.findAllTraByBankAccountId(bankAccountId);

			return ResponseEntity.ok(GenericApiResponse.successResponse(
					MessageFormat.format(CommonConstants.FIND_ALL_TRANSACTION, String.valueOf(bankAccountId)),
					transactions));
		} else {
			// if user not associated to bank account return error response with
			// unauthorized message
			throw new TrackrInputValidationException(CommonConstants.UNAUTHORIZED_ACCESS);
		}
	}

	/**
	 * The purpose of this method is to find a special transaction by
	 * 'transactionId' and 'bankAccountId' value
	 *
	 * @param transactionId this is transaction id
	 * @param bankAccountId this is bank account id
	 * @return ResponseEntity<GenericApiResponse<Transaction>>
	 */
	@GetMapping("/{bankAccountId}/{id}")
	public ResponseEntity<GenericApiResponse<Transaction>> findTransactionById(
			@PathVariable(value = "id") long transactionId, @PathVariable(value = "bankAccountId") long bankAccountId) {

		// verify that user is associated to the requested bank account
		if (isUserAssociatedToBankAccount(bankAccountId)) {
			// get transaction with provided id that is related to this bankAccountId
			Transaction transaction = getTransaction(bankAccountId, transactionId);

			return ResponseEntity.ok(GenericApiResponse.successResponse(
					MessageFormat.format(CommonConstants.RETRIEVE_TRANSACTION, String.valueOf(transactionId)),
					transaction));
		} else {
			// if user not associated to bank account return error response with
			// unauthorized message
			throw new TrackrInputValidationException(CommonConstants.UNAUTHORIZED_ACCESS);
		}
	}

	/**
	 * The purpose of this method is to insert a new transaction record into
	 * TRANSACTION DB table
	 *
	 * @param transactionInput this is a TransactionDTO
	 * @return ResponseEntity<Transaction>
	 */
	@PostMapping
	public ResponseEntity<GenericApiResponse<Transaction>> createTransaction(
			@Valid @RequestBody TransactionDTO transactionInput) {

		// verify that user is associated to the requested bank account
		if (isUserAssociatedToBankAccount(transactionInput.getBankAccountId())) {
			BankAccount bankAccount = getBankAccount(transactionInput.getBankAccountId());
			// create a new transaction record associated to the user
			Transaction transaction = transactionService.createTransaction(transactionInput, bankAccount);

			return ResponseEntity.ok(GenericApiResponse.successResponse(
					MessageFormat.format(CommonConstants.CREATE_TRANSACTION, String.valueOf(transaction.getId())),
					transaction));
		} else {
			// if user not associated to bank account return error response with
			// unauthorized message
			throw new TrackrInputValidationException(CommonConstants.UNAUTHORIZED_ACCESS);
		}
	}

	/**
	 * The purpose of this method is to modify a selected transaction by
	 * 'bankAccountId' value
	 *
	 * @param transactionInput this is a TransactionDTO object
	 * @return ResponseEntity<GenericApiResponse<Transaction>>
	 */
	@PutMapping("/{id}")
	public ResponseEntity<GenericApiResponse<Transaction>> modifyTransaction(
			@PathVariable(value = "id") long transactionId, @RequestBody TransactionDTO transactionInput) {

		// verify that user is associated to the requested bank account
		if (isUserAssociatedToBankAccount(transactionInput.getBankAccountId())) {

			// get existing transaction data, then replace it with provided input
			Transaction transaction = getTransaction(transactionInput.getBankAccountId(), transactionId);
			transaction = transactionService.modifyTransaction(transaction, transactionInput);

			return ResponseEntity.ok(GenericApiResponse.successResponse(
					MessageFormat.format(CommonConstants.MODIFY_TRANSACTION, String.valueOf(transactionId)),
					transaction));
		} else {
			// if user not associated to bank account return error response with
			// unauthorized message
			throw new TrackrInputValidationException(CommonConstants.UNAUTHORIZED_ACCESS);
		}
	}

	/**
	 * The purpose of this method is to valid a transaction by 'transactionId' and
	 * 'bankAccountId' value
	 * 
	 * @param transactionId this is transaction id
	 * @param bankAccountId this is bank account id
	 * @return ResponseEntity<GenericApiResponse<Transaction>>
	 */
	@DeleteMapping("/{bankAccountId}/{id}")
	public ResponseEntity<GenericApiResponse<Transaction>> deleteTransaction(
			@PathVariable(value = "id") long transactionId, @PathVariable(value = "bankAccountId") long bankAccountId) {

		// verify that user is associated to the requested bank account
		if (isUserAssociatedToBankAccount(bankAccountId)) {

			// get existing transaction data, then 'delete' the transaction
			Transaction transaction = getTransaction(bankAccountId, transactionId);
			transaction = transactionService.deleteTransaction(transaction);

			return ResponseEntity.ok(GenericApiResponse.successResponse(
					MessageFormat.format(CommonConstants.INVALID_TRANSACTION, String.valueOf(transactionId)),
					transaction));
		} else {
			// if user not associated to bank account return error response with
			// unauthorized message
			throw new TrackrInputValidationException(CommonConstants.UNAUTHORIZED_ACCESS);
		}

	}

	/**
	 * The purpose of this method is to get a transaction record by 'bankAccountId'
	 * and 'transactionId' value
	 *
	 * @param bankAccountId this is bank account id
	 * @param transactionId this is bank account id
	 * @return Transaction
	 * @author Xiaobing Hou
	 * @date 06/01/2022
	 */
	public Transaction getTransaction(long bankAccountId, long transactionId) {
		BankAccount bankAccount = getBankAccount(bankAccountId);
		return transactionService.findTraByIdAndBankAccountId(transactionId, bankAccount.getId());
	}
}
