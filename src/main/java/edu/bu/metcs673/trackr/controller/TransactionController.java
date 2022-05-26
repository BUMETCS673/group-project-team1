package edu.bu.metcs673.trackr.controller;

import edu.bu.metcs673.trackr.api.GenericApiResponse1;
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
import java.util.Objects;

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
    public ResponseEntity<GenericApiResponse1<Transaction>> createTransaction(@Valid @RequestBody TransactionDTO transactionInput) {

        BankAccount bankAccount = getBankAccount(transactionInput.getBankAccountId());

        // create a new transaction record associated to the user making the API request.
        Transaction transaction = transactionService.createTransaction(transactionInput, bankAccount);

        return new ResponseEntity<>(
                GenericApiResponse1.successResponse(
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
    public ResponseEntity<GenericApiResponse1<Transaction>> findTransactionById(@PathVariable(value = "transactionId") long transactionId,
                                                                                @PathVariable(value = "bankAccountId") long bankAccountId) {

        BankAccount bankAccount = getBankAccount(bankAccountId);
        Transaction transaction = transactionService.findTraByIdAndBankAccountId(transactionId, bankAccount.getId());

        return new ResponseEntity<>(
                GenericApiResponse1.successResponse(
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
    public ResponseEntity<GenericApiResponse1<List<Transaction>>> findAllTransactionById(@PathVariable(value = "bankAccountId") long bankAccountId) {

        BankAccount bankAccount = getBankAccount(bankAccountId);

        List<Transaction> transactions = transactionService.findAllTraByBankAccountId(bankAccount.getId());

        return new ResponseEntity<>(
                GenericApiResponse1.successResponse(
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
    public ResponseEntity<GenericApiResponse1<Transaction>> modifyTransaction(@PathVariable(value = "transactionId") long transactionId,
                                                                              @RequestBody TransactionDTO transactionInput) {

        ResponseEntity<GenericApiResponse1<Transaction>> response = findTransactionById(transactionId, transactionInput.getBankAccountId());

        Transaction transaction = Objects.requireNonNull(response.getBody()).getAdditionalData();
        transaction = transactionService.modifyTransaction(transaction, transactionInput);

        return new ResponseEntity<>(
                GenericApiResponse1.successResponse(
                        MessageFormat.format(CommonConstants.MODIFY_TRANSACTION, String.valueOf(transactionId)),
                        transaction),
                HttpStatus.OK);
    }

    /**
     * The purpose of this method is to valid a transaction by 'transactionId' and 'bankAccountId' value
     *
     * @param transactionId this is transaction id
     * @param bankAccountId this is bank account id
     * @return ResponseEntity<GenericApiResponse>
     * @author Xiaobing Hou
     * @date 05/23/2022
     */
    @DeleteMapping("/delete/{transactionId}/{bankAccountId}")
    public ResponseEntity<GenericApiResponse1<Transaction>> deleteTransaction(@PathVariable(value = "transactionId") long transactionId,
                                                                              @PathVariable(value = "bankAccountId") long bankAccountId) {

        ResponseEntity<GenericApiResponse1<Transaction>> response = findTransactionById(transactionId, bankAccountId);

        Transaction transaction = Objects.requireNonNull(response.getBody()).getAdditionalData();
        transaction = transactionService.deleteTransaction(transaction);

        return new ResponseEntity<>(
                GenericApiResponse1.successResponse(
                        MessageFormat.format(CommonConstants.INVALID_TRANSACTION, String.valueOf(transactionId)),
                        transaction),
                HttpStatus.OK);
    }

    /**
     * The purpose of this method is to get a bank account by 'bankAccountId' value
     *
     * @param bankAccountId this is bank account id
     * @return BankAccount
     * @author Xiaobing Hou
     * @date 05/25/2022
     */
    public BankAccount getBankAccount(long bankAccountId) {
        // pull username from JWT token, find corresponding user record
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        TrackrUser user = trackrUserService.findByUsername(username);
        return bankAccountService.findBankAccountByIdAndUserId(bankAccountId, user.getId());
    }


}
