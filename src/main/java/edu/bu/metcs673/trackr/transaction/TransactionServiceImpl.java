package edu.bu.metcs673.trackr.transaction;

import edu.bu.metcs673.trackr.bankaccount.BankAccount;
import edu.bu.metcs673.trackr.bankaccount.BankAccount.ACCOUNT_STATUS;
import edu.bu.metcs673.trackr.bankaccount.BankAccountRepository;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.common.TrackrInputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Defines logic for the "TransactionService" methods. Calls methods in the
 * transactionRepository to interact with the database.
 *
 * @author Xiaobing Hou
 * @date 05/23/2022
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    /**
     * The purpose of this method is to create a transaction in a bank account by
     * 'transactionInput' and 'bankAccount' value.
     *
     * @param transactionInput this is a TransactionDTO object which gotten from the
     *                         request body
     * @param bankAccount      this is a bank account object which you will insert a
     *                         transaction into
     * @return Transaction
     * @author Xiaobing Hou
     * @date 05/23/2022
     */
    @Override
    public Transaction createTransaction(TransactionDTO transactionInput, BankAccount bankAccount) {
        // create new Transaction object using DTO fields, set bank account as well
        Transaction transaction = new Transaction(transactionInput, bankAccount);

        return transactionRepository.save(transaction);
    }

    /**
     * The purpose of this method is to find a special transaction by
     * 'transactionId' and 'bankAccountId' value.
     *
     * @param transactionId this is transaction id
     * @param bankAccountId This is the id of the corresponding bank account
     * @return Transaction
     * @author Xiaobing Hou
     * @date 05/23/2022
     */
    @Override
    public Transaction findTraByIdAndBankAccountId(long transactionId, long bankAccountId) {
        Transaction transaction = transactionRepository.findTraByIdAndBankAccountId(transactionId, bankAccountId);
        if (transaction != null) {
            return transaction;
        }
        throw new TrackrInputValidationException(CommonConstants.INVALID_TRANSACTION_ID);
    }

    /**
     * The purpose of this method is to modify a special transaction record by
     * 'transaction' and 'transactionInput' value.
     *
     * @param transaction      this is a Transaction object
     * @param transactionInput This is a TransactionDTO object
     * @author Xiaobing Hou
     * @date 05/23/2022
     */
    @Override
    public Transaction modifyTransaction(Transaction transaction, TransactionDTO transactionInput) {
        transaction.setMoney(transactionInput.getMoney());
        transaction.setTime(transactionInput.getTime());
        transaction.setCounterparty(transactionInput.getCounterparty());
        transaction.setTransactionDescription(transactionInput.getTransactionDescription());
        transactionRepository.save(transaction);
        return transaction;
    }

    /**
     * The purpose of this method is to fina all the transaction of a bank account
     *
     * @param bankAccountId this is bank account id
     * @return Transaction
     * @author Xiaobing Hou
     * @date 05/23/2022
     */
    @Override
    public List<Transaction> findAllTraByBankAccountId(long bankAccountId) {
        return transactionRepository.findAllTraByBankAccountIdAndStatus(bankAccountId,
                Transaction.TRANSACTION_STATUS.VALID);
    }

    /**
     * The purpose of this method is to invalid a transaction
     *
     * @param transaction this is a Transaction object
     * @return Transaction
     * @author Xiaobing Hou
     * @date 05/25/2022
     */
    @Override
    public Transaction deleteTransaction(Transaction transaction) {
        transaction.setStatus(Transaction.TRANSACTION_STATUS.INVALID);
        transactionRepository.save(transaction);
        return transaction;
    }

    /**
     * The purpose of this method is to get all valid transaction
     *
     * @param userId
     * @return List<Transaction>
     * @author Tim and Xiaobing
     */
    @Override
    public List<Transaction> findAllTransactions(long userId) {


        List<Long> bankAccountIds = bankAccountRepository.findAllByUserIdAndStatus(userId, ACCOUNT_STATUS.ACTIVE)
                .stream().map(BankAccount::getId).collect(Collectors.toList());

        return transactionRepository.findByBankAccountIdInAndStatusOrderByTimeDesc(bankAccountIds, Transaction.TRANSACTION_STATUS.VALID);
    }
}
