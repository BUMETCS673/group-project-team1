package edu.bu.metcs673.trackr.controller;

import edu.bu.metcs673.trackr.api.BankAccountDTO;
import edu.bu.metcs673.trackr.domain.BankAccount;
import edu.bu.metcs673.trackr.domain.TrackrUser;
import edu.bu.metcs673.trackr.service.BankAccountService;
import edu.bu.metcs673.trackr.service.TrackrUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/v1/bank-account")
public class BankAccountController {

	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private TrackrUserService trackrUserService;
	
	@PostMapping
	public ResponseEntity<BankAccount> createBankAccount(@Valid @RequestBody BankAccountDTO bankAccountInput){
		
		// pull username from JWT token, find corresponding user record
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		TrackrUser user = trackrUserService.findByUsername(username);
		
		// create a new bank account record associated to the user making the API request.
		BankAccount bankAccount = bankAccountService.createBankAccount(bankAccountInput, user);
		
		return new ResponseEntity<BankAccount>(bankAccount, HttpStatus.OK);
	}
}
