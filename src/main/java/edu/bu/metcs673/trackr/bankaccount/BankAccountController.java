package edu.bu.metcs673.trackr.bankaccount;

import java.text.MessageFormat;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
import edu.bu.metcs673.trackr.api.GenericApiResponse1;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.user.TrackrUser;
import edu.bu.metcs673.trackr.user.TrackrUserService;

/**
 * Controller for Bank Account Management. Contains APIs for CRUD (create, read,
 * update, delete) operations related to BankAccount records.
 *
 * @author Tim Flucker
 */
@Validated
@RestController
@RequestMapping("/api/v1/bank-accounts")
public class BankAccountController {

	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private TrackrUserService trackrUserService;

	@PostMapping
	public ResponseEntity<BankAccount> createBankAccount(@Valid @RequestBody BankAccountDTO bankAccountInput) {

		// create a new bank account record associated to the user making the API
		// request.
		BankAccount bankAccount = bankAccountService.createBankAccount(bankAccountInput, getUser());

		return new ResponseEntity<>(bankAccount, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BankAccount> modifyBankAccount(@Valid @RequestBody BankAccountDTO bankAccountInput,
			@PathVariable(value = "id") long id) {

		// Modifies an existing bank account record associated to the user making the
		// API request.
		BankAccount bankAccount = bankAccountService.modifyBankAccount(bankAccountInput, getUser(), id);

		return new ResponseEntity<>(bankAccount, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<GenericApiResponse> modifyBankAccount(@PathVariable(value = "id") long id) {

		// sets status in bank account record associated to the user making the API
		// request to "INACTIVE". This will prevent new transactions from being
		// associated to it.
		bankAccountService.deactivateBankAccount(getUser(), id);

		return new ResponseEntity<>(
				GenericApiResponse.successResponse(
						MessageFormat.format(CommonConstants.DEACTIVATE_BANK_ACCOUNT, String.valueOf(id))),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GenericApiResponse1<BankAccount>> findBankAccount(@PathVariable(value = "id") long id) {
		long userId = getUser().getId();
		BankAccount bankAccount = bankAccountService.findBankAccountByIdAndUserId(id, userId);
		return new ResponseEntity<>(
				GenericApiResponse1.successResponse(
						MessageFormat.format(CommonConstants.FIND_BANKACCOUNT, String.valueOf(userId)), bankAccount),
				HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<GenericApiResponse1<List<BankAccount>>> findAll() {
		List<BankAccount> bankAccounts = bankAccountService.findBankAccountsByUserId(getUser().getId());

		return new ResponseEntity<>(GenericApiResponse1.successResponse(
				MessageFormat.format(CommonConstants.FIND_ALL_BANKACCOUNT, String.valueOf(bankAccounts)), bankAccounts),
				HttpStatus.OK);
	}

	/**
	 * The purpose of this method is to get the current user
	 *
	 * @return TrackrUser
	 * @author Xiaobing Hou
	 * @date 06/01/2022
	 */
	public TrackrUser getUser() {
		// pull username from JWT token, find corresponding user record
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		return trackrUserService.findByUsername(username);
	}
}
