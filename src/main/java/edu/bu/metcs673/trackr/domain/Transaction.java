package edu.bu.metcs673.trackr.domain;

import edu.bu.metcs673.trackr.api.TransactionDTO;
import edu.bu.metcs673.trackr.common.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "bankAccountId", referencedColumnName = "id", nullable = false)
    private BankAccount bankAccount;

    @Column(nullable = false, precision = 2)
    private double money;

    @Column(nullable = false, length = 100)
    private String time;

    @Column(nullable = false)
    @Size(max = 100, message = CommonConstants.INVALID_TRANSACTION_COU_LENGTH)
    private String counterparty;

    @Column
    @Size(max = 255, message = CommonConstants.INVALID_TRANSACTION_TD_LENGTH)
    private String transactionDescription;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TRANSACTION_STATUS status;

    public Transaction(TransactionDTO transactionInput, BankAccount bankAccount) {
        this.id = 0L;
        this.bankAccount = bankAccount;
        this.money = transactionInput.getMoney();
        this.time = transactionInput.getTime();
        this.counterparty = transactionInput.getCounterparty();
        this.transactionDescription = transactionInput.getTransactionDescription();
        this.status = TRANSACTION_STATUS.VALID;
    }


    // enum used for the 'status' field in the Transaction object
    public enum TRANSACTION_STATUS {
        VALID("valid", "This transaction is valid"),
        INVALID("invalid", "This transaction has been invalid");

        private final String name;
        private final String desc;

        TRANSACTION_STATUS(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }

        public boolean contains(String status) {
            for (TRANSACTION_STATUS acctStatus : TRANSACTION_STATUS.values()) {
                if (StringUtils.equals(acctStatus.toString(), status)) {
                    return true;
                }
            }
            return false;
        }
    }

    ;

}
