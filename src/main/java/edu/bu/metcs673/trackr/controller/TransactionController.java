package edu.bu.metcs673.trackr.controller;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.api.TransactionDTO;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.domain.BankAccount;
import edu.bu.metcs673.trackr.domain.TrackrUser;
import edu.bu.metcs673.trackr.domain.Transaction;
import edu.bu.metcs673.trackr.service.BankAccountService;
import edu.bu.metcs673.trackr.service.TrackrUserService;
import edu.bu.metcs673.trackr.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;

/**
 * Controller for Transactions Management. Contains 'Create', 'Find', 'Modify', and 'Delete' APIs
 *
 * @author Xiaobing Hou
 * @date 05/21/2022
 */
@Validated
@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    @Autowired
    private TrackrUserService trackrUserService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransactionService transactionService;

    /**
     * The purpose of this method is to insert a new transaction record into TRANSACTION DB table
     *
     * @param transactionInput this is a TransactionDTO
     * @return ResponseEntity<Transaction>
     * @author Xiaobing Hou
     * @date 05/21/2022
     */
    @PostMapping
    public ResponseEntity<GenericApiResponse> createTransaction(@Valid @RequestBody TransactionDTO transactionInput) {

        // pull username from JWT token, find corresponding user record
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        TrackrUser user = trackrUserService.findByUsername(username);

        BankAccount bankAccount = bankAccountService.findBankAccountByIdAndUserId(transactionInput.getBankAccountId(), user.getId());

        // create a new transaction record associated to the user making the API request.
        Transaction transaction = transactionService.createTransaction(transactionInput, bankAccount);

        return new ResponseEntity<>(
                GenericApiResponse.successResponse(
                        MessageFormat.format(CommonConstants.CREATE_TRANSACTION, String.valueOf(transaction.getId())),
                        transaction),
                HttpStatus.OK);
    }


    /**
     * The purpose of this method is to find a special transaction by 'transactionId'  and 'bankAccountId' value
     *
     * @param transactionId this is transaction id
     * @param bankAccountId this is bank account id
     * @return ResponseEntity<Transaction>
     * @author Xiaobing Hou
     * @date 05/22/2022
     */
    @GetMapping("/find/{transactionId}/{bankAccountId}")
    public ResponseEntity<GenericApiResponse> findTransactionById(@PathVariable(value = "transactionId") long transactionId,
                                                                  @PathVariable(value = "bankAccountId") long bankAccountId) {
        // Pull username from JWT token
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        TrackrUser user = trackrUserService.findByUsername(username);

        BankAccount bankAccount = bankAccountService.findBankAccountByIdAndUserId(bankAccountId, user.getId());

        Transaction transaction = transactionService.findTraByIdAndBankAccountId(transactionId, bankAccount.getId());

        return new ResponseEntity<>(
                GenericApiResponse.successResponse(
                        MessageFormat.format(CommonConstants.FIND_TRANSACTION, String.valueOf(transactionId)),
                        transaction),
                HttpStatus.OK);

    }

    /**
     * The purpose of this method is to find all transaction by 'bankAccountId' value
     *
     * @param bankAccountId this is bank account id
     * @return ResponseEntity<Transaction>
     * @author Xiaobing Hou
     * @date 05/23/2022
     */
    @GetMapping("/findAll/{bankAccountId}")
    public ResponseEntity<GenericApiResponse> findAllTransactionById(@PathVariable(value = "bankAccountId") long bankAccountId) {
        // pull username from JWT token
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        TrackrUser user = trackrUserService.findByUsername(username);

        BankAccount bankAccount = bankAccountService.findBankAccountByIdAndUserId(bankAccountId, user.getId());

        List<Transaction> transactions = transactionService.findAllTraByBankAccountId(bankAccount.getId());

        return new ResponseEntity<>(
                GenericApiResponse.successResponse(
                        MessageFormat.format(CommonConstants.FIND_ALL_TRANSACTION, String.valueOf(bankAccountId)),
                        transactions),
                HttpStatus.OK);
    }

    /**
     * The purpose of this method is to modify a selected transaction by 'bankAccountId' value
     *
     * @param transactionId    this is transaction id
     * @param transactionInput this is a TransactionDTO object
     * @return ResponseEntity<GenericApiResponse>
     * @author Xiaobing Hou
     * @date 05/23/2022
     */
    @PostMapping("/modify/{transactionId}")
    public ResponseEntity<GenericApiResponse> modifyTransaction(@PathVariable(value = "transactionId") long transactionId,
                                                                @RequestBody TransactionDTO transactionInput) {
        // pull username from JWT token
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        TrackrUser user = trackrUserService.findByUsername(username);

        BankAccount bankAccount = bankAccountService.findBankAccountByIdAndUserId(transactionInput.getBankAccountId(), user.getId());

        Transaction transaction = transactionService.findTraByIdAndBankAccountId(transactionId, bankAccount.getId());

        transaction = transactionService.modifyTransaction(transaction, transactionInput);

        return new ResponseEntity<>(
                GenericApiResponse.successResponse(
                        MessageFormat.format(CommonConstants.MODIFY_TRANSACTION, String.valueOf(transactionId)),
                        transaction),
                HttpStatus.OK);
    }

}
