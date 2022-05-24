package edu.bu.metcs673.trackr.repo;

import edu.bu.metcs673.trackr.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    //Code by Xiaobing Hou. YOu can delete or change this part
    public BankAccount findBankAccountByIdAndUserId(long bankAccountId, long userId);

}
