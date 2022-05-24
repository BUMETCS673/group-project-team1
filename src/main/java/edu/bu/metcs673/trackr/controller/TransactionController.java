package edu.bu.metcs673.trackr.controller;

import edu.bu.metcs673.trackr.api.TransactionDTO;
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
import java.util.List;

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


    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionDTO transactionInput) {

        // pull username from JWT token, find corresponding user record
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        TrackrUser user = trackrUserService.findByUsername(username);

        BankAccount bankAccount = bankAccountService.findBankAccountByIdAndUserId(transactionInput.getBankAccountId(), user.getId());

        // create a new bank account record associated to the user making the API request.
        Transaction transaction = transactionService.createTransaction(transactionInput, bankAccount);

        return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Transaction> findTransactionById(@RequestParam("transactionId") long transactionId, @RequestParam("bankAccountId") long bankAccountId) {
        // pull username from JWT token
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        TrackrUser user = trackrUserService.findByUsername(username);

        BankAccount bankAccount = bankAccountService.findBankAccountByIdAndUserId(bankAccountId, user.getId());

        Transaction transaction = transactionService.findTraByIdAndBankAccountId(transactionId, bankAccount.getId());
        return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);

    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Transaction>> findTransactionById(@RequestParam("bankAccountId") long bankAccountId) {
        // pull username from JWT token
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        TrackrUser user = trackrUserService.findByUsername(username);

        BankAccount bankAccount = bankAccountService.findBankAccountByIdAndUserId(bankAccountId, user.getId());

        List<Transaction> transactions = transactionService.findAllTraByBankAccountId(bankAccount.getId());

        return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
    }

    @PostMapping("/modify")
    public ResponseEntity<Transaction> modifyTransaction(@RequestParam("transactionId") long transactionId, @RequestBody TransactionDTO transactionInput) {
        // pull username from JWT token
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        TrackrUser user = trackrUserService.findByUsername(username);

        BankAccount bankAccount = bankAccountService.findBankAccountByIdAndUserId(transactionInput.getBankAccountId(), user.getId());

        Transaction transaction = transactionService.findTraByIdAndBankAccountId(transactionId, bankAccount.getId());

        transactionService.modifyTransaction(transaction, transactionInput);

        return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
    }

}
