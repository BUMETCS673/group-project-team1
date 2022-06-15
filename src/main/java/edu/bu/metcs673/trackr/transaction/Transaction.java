package edu.bu.metcs673.trackr.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import edu.bu.metcs673.trackr.bankaccount.BankAccount;
import edu.bu.metcs673.trackr.common.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACTIONS")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
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

	// enum used for the 'status' field in the Transaction object
	public static enum TRANSACTION_STATUS {
		VALID, INVALID;

		public boolean contains(String status) {
			for (TRANSACTION_STATUS acctStatus : TRANSACTION_STATUS.values()) {
				if (StringUtils.equals(acctStatus.toString(), status)) {
					return true;
				}
			}
			return false;
		}
	};

	public Transaction(TransactionDTO transactionInput, BankAccount bankAccount) {
		this.id = 0L;
		this.bankAccount = bankAccount;
		this.money = transactionInput.getMoney();
		this.time = transactionInput.getTime();
		this.counterparty = transactionInput.getCounterparty();
		this.transactionDescription = transactionInput.getTransactionDescription();
		this.status = TRANSACTION_STATUS.VALID;
	}
}
