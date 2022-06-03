package edu.bu.metcs673.trackr.controller;

import edu.bu.metcs673.trackr.api.BankAccountDTO;
import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.api.GenericApiResponse1;

import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.domain.BankAccount;
import edu.bu.metcs673.trackr.domain.TrackrUser;
import edu.bu.metcs673.trackr.service.BankAccountService;
import edu.bu.metcs673.trackr.service.TrackrUserService;
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

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;

/**
 * Controller for Bank Account Management. Contains APIs for CRUD (create, read,
 * update, delete) operations related to BankAccount records.
 *
 * @author Tim Flucker
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TrackrUserService trackrUserService;

    @PostMapping("/bank-accounts")
    public ResponseEntity<BankAccount> createBankAccount(@Valid @RequestBody BankAccountDTO bankAccountInput) {

        // create a new bank account record associated to the user making the API
        // request.
        BankAccount bankAccount = bankAccountService.createBankAccount(bankAccountInput, getUser());

        return new ResponseEntity<BankAccount>(bankAccount, HttpStatus.OK);
    }

    @PutMapping("/bank-accounts/{id}")
    public ResponseEntity<BankAccount> modifyBankAccount(@Valid @RequestBody BankAccountDTO bankAccountInput,
                                                         @PathVariable(value = "id") long id) {

        // Modifies an existing bank account record associated to the user making the
        // API request.
        BankAccount bankAccount = bankAccountService.modifyBankAccount(bankAccountInput, getUser(), id);

        return new ResponseEntity<BankAccount>(bankAccount, HttpStatus.OK);
    }

    @DeleteMapping("/bank-accounts/{id}")
    public ResponseEntity<GenericApiResponse> modifyBankAccount(@PathVariable(value = "id") long id) {

        // sets status in bank account record associated to the user making the API
        // request to "INACTIVE". This will prevent new transactions from being
        // associated to it.
        bankAccountService.deactivateBankAccount(getUser(), id);

        return new ResponseEntity<GenericApiResponse>(
                GenericApiResponse.successResponse(
                        MessageFormat.format(CommonConstants.DEACTIVATE_BANK_ACCOUNT, String.valueOf(id))),
                HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/bank-accounts/{bankAccountId}/")
    public ResponseEntity<GenericApiResponse1<BankAccount>> findBankAccount(
            @PathVariable(value = "bankAccountId") long bankAccountId,
            @PathVariable(value = "userId") long userId
    ) {
        BankAccount bankAccount = bankAccountService.findByBankAccountIdAndUserId(bankAccountId, userId);
        return new ResponseEntity<>(
                GenericApiResponse1.successResponse(
                        MessageFormat.format(CommonConstants.FIND_BANKACCOUNT, String.valueOf(userId)),
                        bankAccount
                ),
                HttpStatus.OK
        );

    }
    @GetMapping("/users/{userId}/bank-accounts")
    public ResponseEntity<GenericApiResponse1<List<BankAccount>>> findAll(@PathVariable(value = "userId")long userID) {

        List<BankAccount> bankAccounts = bankAccountService.findAllBankAccount(userID);

        return new ResponseEntity<>(
                GenericApiResponse1.successResponse(
                        MessageFormat.format(CommonConstants.FIND_ALL_BANKACCOUNT,String.valueOf(bankAccounts)),
                        bankAccounts),
                HttpStatus.OK);


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
