package edu.bu.metcs673.trackr.domain;

import edu.bu.metcs673.trackr.common.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BANK_ACCOUNT")
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
	private TrackrUser user;

	@Column(nullable = false, length = 20)
	@NotNull(message = CommonConstants.BLANK_ACCOUNT_TYPE)
	@Enumerated(EnumType.STRING)
	private ACCOUNT_TYPE accountType;

	@Column
	@Size(min = 0, max = 255, message = CommonConstants.INVALID_ACCOUNT_DESC_LENGTH)
	private String accountDescription;

	@Column(nullable = false, precision = 2)
	@PositiveOrZero(message = CommonConstants.INVALID_BALANCE_VALUE)
	private double balance;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ACCOUNT_STATUS status;

	// enum used for the 'status' field in the BankAccount object
	public static enum ACCOUNT_STATUS {
		ACTIVE, INACTIVE;

		public boolean contains(String status) {
			for (ACCOUNT_STATUS acctStatus : ACCOUNT_STATUS.values()) {
				if (StringUtils.equals(acctStatus.toString(), status)) {
					return true;
				}
			}
			return false;
		}
	};

	// enum used for the 'accountType' field in the BankAccount object
	public static enum ACCOUNT_TYPE {
		CHECKING, SAVING, RETIREMENT, OTHER;

		public boolean contains(String status) {
			for (ACCOUNT_TYPE acctStatus : ACCOUNT_TYPE.values()) {
				if (StringUtils.equals(acctStatus.toString(), status)) {
					return true;
				}
			}
			return false;
		}
	}
}
