package edu.bu.metcs673.trackr.service.impl;

import edu.bu.metcs673.trackr.api.BankAccountDTO;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.common.TrackrInputValidationException;
import edu.bu.metcs673.trackr.domain.BankAccount;
import edu.bu.metcs673.trackr.domain.BankAccount.ACCOUNT_STATUS;
import edu.bu.metcs673.trackr.domain.TrackrUser;
import edu.bu.metcs673.trackr.repo.BankAccountRepository;
import edu.bu.metcs673.trackr.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Defines logic for the "BankAccountService" methods. Calls methods in the
 * bankAccountRepository to interact with the database.
 *
 * @author Tim Flucker
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount createBankAccount(BankAccountDTO bankAccountInput, TrackrUser user) {

        // create new bank account object using DTO fields, set user and accountStatus
        // as well
        BankAccount bankAccount = new BankAccount(0L, user, bankAccountInput.getAccountType(),
                bankAccountInput.getAccountDescription(), bankAccountInput.getBalance(),
                BankAccount.ACCOUNT_STATUS.ACTIVE);

        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount modifyBankAccount(BankAccountDTO bankAccountInput, TrackrUser user, long id) {

        // verify that user making request is the same user associated to the DB record,
        // throws exception if data does not match.
        BankAccount bankAccount = verifyUser(user, id);

        // overwrite values for DB record, with provided DTO values
        bankAccount.setAccountDescription(bankAccountInput.getAccountDescription());
        bankAccount.setAccountType(bankAccountInput.getAccountType());
        bankAccount.setBalance(bankAccountInput.getBalance());

        // save modified record to DB
        bankAccountRepository.save(bankAccount);

        return bankAccount;
    }

    @Override
    public void deactivateBankAccount(TrackrUser user, long id) {
        // verify that user making request is the same user associated to the DB record,
        // throws exception if data does not match.
        BankAccount bankAccount = verifyUser(user, id);

        // set "INACTIVE" status, then save to DB
        bankAccount.setStatus(ACCOUNT_STATUS.INACTIVE);
        bankAccountRepository.save(bankAccount);
    }

    public BankAccount verifyUser(TrackrUser user, long id) {
        // get bank account based on id method parameter
        BankAccount bankAccount = bankAccountRepository.findById(id).get();

        // verify that user making request is the same user associated to the DB record
        if (user.getId() != bankAccount.getUser().getId()) {
            throw new TrackrInputValidationException(CommonConstants.UNAUTHORIZED_ACCESS);
        }

        return bankAccount;
    }


    /**
     * The purpose of this method is to find a special bank account by its 'id' and 'userId' value.
     *
     * @param bankAccountId this is transaction id
     * @param userId this is user id
     * @return BankAccount
     * @author Xiaobing Hou
     */
    @Override
    public BankAccount findByBankAccountIdAndUserId(long bankAccountId, long userId) {
        BankAccount bankAccount = bankAccountRepository.findByIdAndUserId(bankAccountId, userId);
        if (bankAccount != null) {
            return bankAccount;
        }
        throw new TrackrInputValidationException(CommonConstants.INVALID_BANK_ACCOUNT_ID);

    }

    @Override
    public  List<BankAccount> findAllBankAccount(long userId) {
        return bankAccountRepository.findAllByUserId(userId);
    }
}
