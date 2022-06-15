package edu.bu.metcs673.trackr.bankaccount;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Directly interfaces with the "BANK_ACCOUNT" table in the H2 repository, using the
 * JPA library.
 *
 * @author Tim Flucker
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    /**
     * Custom JPA query that uses 'bankAccountId' and 'userId' to gets the bank account object from the DB
     *
     * @param id this is bank account id
     * @param userId this is user id
     * @param status Bank account status
     * @return BankAccount
     * @author Xiaobing Hou
     */
    public BankAccount findByIdAndUserIdAndStatus(long id, long userId, BankAccount.ACCOUNT_STATUS status);

    /**
     * Find all by user id and status.
     *
     * @param userId User id
     * @param status bank account status
     * @return List of accounts
     */
    public List<BankAccount> findAllByUserIdAndStatus(long userId, BankAccount.ACCOUNT_STATUS status);
}
