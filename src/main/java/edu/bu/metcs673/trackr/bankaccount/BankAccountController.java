package edu.bu.metcs673.trackr.bankaccount;

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
import edu.bu.metcs673.trackr.common.BaseController;
import edu.bu.metcs673.trackr.common.CommonConstants;

/**
 * Controller for Bank Account Management. Contains APIs for CRUD (create, read,
 * update, delete) operations related to BankAccount records.
 *
 * @author Tim Flucker
 */
@Validated
@RestController
@RequestMapping("/api/v1/bank-accounts")
public class BankAccountController extends BaseController {

	@Autowired
	private BankAccountService bankAccountService;

	/**
	 * Returns all Bank Account records associated to the current user.
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<GenericApiResponse<List<BankAccount>>> findAll() {
		List<BankAccount> bankAccounts = bankAccountService.findBankAccountsByUserId(getUser().getId());

		return ResponseEntity.ok(GenericApiResponse.successResponse(
				MessageFormat.format(CommonConstants.FIND_ALL_BANK_ACCOUNT, String.valueOf(bankAccounts)),
				bankAccounts));
	}

	/**
	 * Returns a specific Bank Account record associated to the current user.
	 * 
	 * @param bankAccountId
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<GenericApiResponse<BankAccount>> findBankAccount(
			@PathVariable(value = "id") long bankAccountId) {
		long userId = getUser().getId();
		BankAccount bankAccount = bankAccountService.findBankAccountByIdAndUserId(bankAccountId, userId);

		return ResponseEntity.ok(GenericApiResponse.successResponse(
				MessageFormat.format(CommonConstants.RETRIEVE_BANK_ACCOUNT, String.valueOf(userId)), bankAccount));
	}

	/**
	 * Creates a new Bank Account record associated to the current user with the
	 * provided input as the record data.
	 * 
	 * @param bankAccountInput
	 * @return
	 */
	@PostMapping
	public ResponseEntity<GenericApiResponse<BankAccount>> createBankAccount(
			@Valid @RequestBody BankAccountDTO bankAccountInput) {

		// create a new bank account record associated to the user making the API
		// request.
		BankAccount bankAccount = bankAccountService.createBankAccount(bankAccountInput, getUser());

		return ResponseEntity.ok(GenericApiResponse.successResponse(
				MessageFormat.format(CommonConstants.CREATE_BANK_ACCOUNT, bankAccount.getId()), bankAccount));
	}

	/**
	 * Modifies an existing Bank Account record with the provided id value. If user
	 * making the API request is the same as the user associated to the record, then
	 * replace data with provided request body values.
	 * 
	 * @param bankAccountInput
	 * @param id
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<GenericApiResponse<BankAccount>> modifyBankAccount(
			@Valid @RequestBody BankAccountDTO bankAccountInput, @PathVariable(value = "id") long id) {

		// Modifies an existing bank account record associated to the user making the
		// API request.
		BankAccount bankAccount = bankAccountService.modifyBankAccount(bankAccountInput, getUser().getId(), id);

		return ResponseEntity.ok(GenericApiResponse.successResponse(CommonConstants.MODIFY_TRANSACTION, bankAccount));
	}

	/**
	 * Invalidates an existing Bank Account record with the provided id value. If
	 * user making the API request is the same as the user associated to the record,
	 * then set status of Bank Account record to 'INACTIVE'.
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<GenericApiResponse<BankAccount>> deactivateBankAccount(@PathVariable(value = "id") long id) {

		// sets status in bank account record associated to the user making the API
		// request to "INACTIVE". This will prevent new transactions from being
		// associated to it.
		bankAccountService.deactivateBankAccount(getUser().getId(), id);

		return ResponseEntity.ok(GenericApiResponse
				.successResponse(MessageFormat.format(CommonConstants.DEACTIVATE_BANK_ACCOUNT, String.valueOf(id))));
	}
}
