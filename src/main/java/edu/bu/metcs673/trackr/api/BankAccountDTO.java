package edu.bu.metcs673.trackr.api;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDTO {

	@NotNull(message = CommonConstants.BLANK_ACCOUNT_TYPE)
	@Enumerated(EnumType.STRING)
	private BankAccount.ACCOUNT_TYPE accountType;

	@Size(min = 0, max = 255, message = CommonConstants.INVALID_ACCOUNT_DESC_LENGTH)
	private String accountDescription;

	@NotNull(message = CommonConstants.BLANK_BALANCE)
	@PositiveOrZero(message = CommonConstants.INVALID_BALANCE_VALUE)
	private double balance;
}
