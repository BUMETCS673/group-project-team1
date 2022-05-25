package edu.bu.metcs673.trackr.repo;

import edu.bu.metcs673.trackr.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    /**
     * Custom JPA query that finds a user by its 'bankAccountId' and 'userId' value.
     *
     * @param transactionId
     * @param bankAccountId
     * @return
     * @author Xiaobing Hou
     */
    public Transaction findTraByIdAndBankAccountId(long transactionId, long bankAccountId);

    public List<Transaction> findAllTraByBankAccountId(long bankAccountId);

}
