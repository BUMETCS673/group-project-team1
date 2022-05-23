package edu.bu.metcs673.trackr.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.bu.metcs673.trackr.domain.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{

}
