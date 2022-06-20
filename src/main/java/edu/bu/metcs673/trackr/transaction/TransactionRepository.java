package edu.bu.metcs673.trackr.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Directly interfaces with the "TRANSACTION" table in the H2 repository, using the
 * JPA library.
 *
 * @author Xiaobing Hou
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    /**
     * Custom JPA query that finds a transaction by its 'bankAccountId' and 'userId' value.
     *
     * @param transactionId this is transaction id
     * @param bankAccountId this is bank account id
     * @return Transaction
     * @author Xiaobing Hou
     * @date 05/22/2022
     */
    public Transaction findTraByIdAndBankAccountId(long transactionId, long bankAccountId);

    /**
     * Custom JPA query that finds all transaction user by its 'bankAccountId' value.
     *
     * @param bankAccountId this is bank account id
     * @return List<Transaction>
     * @author Xiaobing Hou
     * @date 05/23/2022
     */
    public List<Transaction> findAllTraByBankAccountIdAndStatus(long bankAccountId, Transaction.TRANSACTION_STATUS VALID);

    public List<Transaction> findByBankAccountIdInAndStatusOrderByTimeDesc(List<Long> bankAccountIds, Transaction.TRANSACTION_STATUS VALID);

}
