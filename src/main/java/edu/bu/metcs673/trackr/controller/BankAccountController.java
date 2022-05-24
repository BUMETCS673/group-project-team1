package edu.bu.metcs673.trackr.controller;

import java.text.MessageFormat;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.bu.metcs673.trackr.api.BankAccountDTO;
import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.domain.BankAccount;
import edu.bu.metcs673.trackr.domain.TrackrUser;
import edu.bu.metcs673.trackr.service.BankAccountService;
import edu.bu.metcs673.trackr.service.TrackrUserService;

/**
 * Controller for Bank Account Management. Contains APIs for CRUD (create, read,
 * update, delete) operations related to BankAccount records.
 * 
 * @author Tim Flucker
 *
 */
@Validated
@RestController
@RequestMapping("/api/v1/bank-account")
public class BankAccountController {

	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private TrackrUserService trackrUserService;

	@PostMapping
	public ResponseEntity<BankAccount> createBankAccount(@Valid @RequestBody BankAccountDTO bankAccountInput) {

		// pull username from JWT token, find corresponding user record
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		TrackrUser user = trackrUserService.findByUsername(username);

		// create a new bank account record associated to the user making the API
		// request.
		BankAccount bankAccount = bankAccountService.createBankAccount(bankAccountInput, user);

		return new ResponseEntity<BankAccount>(bankAccount, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BankAccount> modifyBankAccount(@Valid @RequestBody BankAccountDTO bankAccountInput,
			@PathVariable(value = "id") long id) {

		// pull username from JWT token, find corresponding user record
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		TrackrUser user = trackrUserService.findByUsername(username);

		// Modifies an existing bank account record associated to the user making the
		// API request.
		BankAccount bankAccount = bankAccountService.modifyBankAccount(bankAccountInput, user, id);

		return new ResponseEntity<BankAccount>(bankAccount, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<GenericApiResponse> modifyBankAccount(@PathVariable(value = "id") long id) {
		// pull username from JWT token, find corresponding user record
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		TrackrUser user = trackrUserService.findByUsername(username);

		// sets status in bank account record associated to the user making the API
		// request to "INACTIVE". This will prevent new transactions from being
		// associated to it.
		bankAccountService.deactivateBankAccount(user, id);

		return new ResponseEntity<GenericApiResponse>(
				GenericApiResponse.successResponse(
						MessageFormat.format(CommonConstants.DEACTIVATE_BANK_ACCOUNT, String.valueOf(id))),
				HttpStatus.OK);
	}
}
