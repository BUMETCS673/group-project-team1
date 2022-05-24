package edu.bu.metcs673.trackr.service.impl;

import edu.bu.metcs673.trackr.api.TransactionDTO;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.common.TrackrInputValidationException;
import edu.bu.metcs673.trackr.domain.BankAccount;
import edu.bu.metcs673.trackr.domain.Transaction;
import edu.bu.metcs673.trackr.repo.TransactionRepository;
import edu.bu.metcs673.trackr.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public Transaction createTransaction(TransactionDTO transactionInput, BankAccount bankAccount) {
        // create new Transaction object using DTO fields, set bank account as well
        Transaction transaction = new Transaction(transactionInput, bankAccount);

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findTraByIdAndBankAccountId(long transactionId, long bankAccountId) {
        Transaction transaction;
        if ((transaction = transactionRepository.findTraByIdAndBankAccountId(transactionId, bankAccountId)) != null) {
            return transaction;
        }
        throw new TrackrInputValidationException(CommonConstants.INVALID_TRANSACTION_ID);
    }

    @Override
    public Transaction modifyTransaction(Transaction transaction, TransactionDTO transactionInput) {
        transaction.setMoney(transactionInput.getMoney());
        transaction.setTime(transactionInput.getTime());
        transaction.setCounterparty(transactionInput.getCounterparty());
        transaction.setTransactionDescription(transactionInput.getTransactionDescription());
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findAllTraByBankAccountId(long bankAccountId) {
        return transactionRepository.findAllTraByBankAccountId(bankAccountId);
    }
}
