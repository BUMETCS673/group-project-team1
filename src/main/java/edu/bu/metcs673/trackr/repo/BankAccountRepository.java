package edu.bu.metcs673.trackr.repo;

import edu.bu.metcs673.trackr.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * @param userId        this is user id
     * @return BankAccount
     * @author Xiaobing Hou
     * @date 05/23/2022
     */
    public BankAccount findByIdAndUserId(long id, long userId);

    public List<BankAccount> findAllByUserId(long userId);
}
