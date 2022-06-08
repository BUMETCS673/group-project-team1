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
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.common.CommonDataUtil;

/**
 * Controller for Transactions Management. Contains 'Create', 'Find', 'Modify',
 * and 'Delete' APIs
 *
 * @author Xiaobing Hou
 * @date 05/21/2022
 */
@Validated
@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController extends CommonDataUtil {

	@Autowired
	private TransactionService transactionService;

	/**
	 * The purpose of this method is to find all transaction by 'bankAccountId'
	 * value
	 *
	 * @param bankAccountId this is bank account id
	 * @return ResponseEntity<Transaction>
	 * @author Xiaobing Hou
	 * @date 05/23/2022
	 */
	@GetMapping("/findAll/{bankAccountId}")
	public ResponseEntity<GenericApiResponse<List<Transaction>>> findAllTransactionById(
			@PathVariable(value = "bankAccountId") long bankAccountId) {

		BankAccount bankAccount = getBankAccount(bankAccountId);

		List<Transaction> transactions = transactionService.findAllTraByBankAccountId(bankAccount.getId());

		return ResponseEntity.ok(GenericApiResponse.successResponse(
				MessageFormat.format(CommonConstants.FIND_ALL_TRANSACTION, String.valueOf(bankAccountId)),
				transactions));
	}

	/**
	 * The purpose of this method is to find a special transaction by
	 * 'transactionId' and 'bankAccountId' value
	 *
	 * @param transactionId this is transaction id
	 * @param bankAccountId this is bank account id
	 * @return ResponseEntity<Transaction>
	 * @author Xiaobing Hou
	 * @date 05/22/2022
	 */
	@GetMapping("/find/{id}/{bankAccountId}")
	public ResponseEntity<GenericApiResponse<Transaction>> findTransactionById(
			@PathVariable(value = "id") long transactionId,
			@PathVariable(value = "bankAccountId") long bankAccountId) {

		Transaction transaction = getTransaction(bankAccountId, transactionId);

		return ResponseEntity.ok(GenericApiResponse.successResponse(
				MessageFormat.format(CommonConstants.RETRIEVE_TRANSACTION, String.valueOf(transactionId)),
				transaction));
	}

	/**
	 * The purpose of this method is to insert a new transaction record into
	 * TRANSACTION DB table
	 *
	 * @param transactionInput this is a TransactionDTO
	 * @return ResponseEntity<Transaction>
	 * @author Xiaobing Hou
	 * @date 05/21/2022
	 */
	@PostMapping
	public ResponseEntity<GenericApiResponse<Transaction>> createTransaction(
			@Valid @RequestBody TransactionDTO transactionInput) {

		BankAccount bankAccount = getBankAccount(transactionInput.getBankAccountId());

		// create a new transaction record associated to the user making the API
		// request.
		Transaction transaction = transactionService.createTransaction(transactionInput, bankAccount);

		return ResponseEntity.ok(GenericApiResponse.successResponse(
				MessageFormat.format(CommonConstants.CREATE_TRANSACTION, String.valueOf(transaction.getId())),
				transaction));
	}

	/**
	 * The purpose of this method is to modify a selected transaction by
	 * 'bankAccountId' value
	 *
	 * @param id    this is transaction id
	 * @param transactionInput this is a TransactionDTO object
	 * @return ResponseEntity<GenericApiResponse>
	 * @author Xiaobing Hou
	 * @date 05/23/2022
	 */
	@PutMapping("/{id}")
	public ResponseEntity<GenericApiResponse<Transaction>> modifyTransaction(
			@PathVariable(value = "id") long transactionId, @RequestBody TransactionDTO transactionInput) {

		Transaction transaction = getTransaction(transactionInput.getBankAccountId(), transactionId);
		transaction = transactionService.modifyTransaction(transaction, transactionInput);

		return ResponseEntity.ok(GenericApiResponse.successResponse(
				MessageFormat.format(CommonConstants.MODIFY_TRANSACTION, String.valueOf(transactionId)), transaction));
	}

	/**
	 * The purpose of this method is to valid a transaction by 'transactionId' and
	 * 'bankAccountId' value
	 *
	 * @param id this is transaction id
	 * @param bankAccountId this is bank account id
	 * @return ResponseEntity<GenericApiResponse>
	 * @author Xiaobing Hou
	 * @date 05/23/2022
	 */
	@DeleteMapping("/{id}/{bankAccountId}")
	public ResponseEntity<GenericApiResponse<Transaction>> deleteTransaction(
			@PathVariable(value = "id") long transactionId,
			@PathVariable(value = "bankAccountId") long bankAccountId) {

		Transaction transaction = getTransaction(bankAccountId, transactionId);
		transaction = transactionService.deleteTransaction(transaction);

		return ResponseEntity.ok(GenericApiResponse.successResponse(
				MessageFormat.format(CommonConstants.INVALID_TRANSACTION, String.valueOf(transactionId)), transaction));
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
