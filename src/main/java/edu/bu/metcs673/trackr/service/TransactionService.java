package edu.bu.metcs673.trackr.service;

import edu.bu.metcs673.trackr.api.TransactionDTO;
import edu.bu.metcs673.trackr.domain.BankAccount;
import edu.bu.metcs673.trackr.domain.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Interface which defines methods which will be implemented in the
 * "TransactionServiceImpl". This interface is reusable, so other classes could extend
 * this if they wanted / needed to.
 *
 * @author Xiaobing Hou
 * @date 05/23/2022
 */
@Component
public interface TransactionService {

    /**
     * Creates a new transaction using the provided object, associate to the bank account
     * making the request. Fields not provided by the user will be filled in with
     * their default values.
     *
     * @param transactionInput
     * @return
     */
    public Transaction createTransaction(TransactionDTO transactionInput, BankAccount bankAccount);

    /**
     * Creates a new transaction using the provided object, associate to the bank account
     * making the request. Fields not provided by the user will be filled in with
     * their default values.
     *
     * @param transactionId
     * @param bankAccountId
     * @return
     */
    public Transaction findTraByIdAndBankAccountId(long transactionId, long bankAccountId);

    /**
     * Creates a new transaction using the provided object, associate to the bank account
     * making the request. Fields not provided by the user will be filled in with
     * their default values.
     *
     * @param transaction
     * @param transactionInput
     * @return
     */
    public Transaction modifyTransaction(Transaction transaction, TransactionDTO transactionInput);


    public List<Transaction> findAllTraByBankAccountId(long bankAccountId);
}
