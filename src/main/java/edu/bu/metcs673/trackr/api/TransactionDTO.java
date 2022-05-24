package edu.bu.metcs673.trackr.api;

import edu.bu.metcs673.trackr.common.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class TransactionDTO {

	@NotNull(message = CommonConstants.TRANSACTION_MONEY)
	private long bankAccountId;

	@NotNull(message = CommonConstants.TRANSACTION_MONEY)
	private double money;

	@NotNull(message = CommonConstants.TRANSACTION_TIME)
	private String time;

	@NotNull(message = CommonConstants.TRANSACTION_COU)
	@Size(max = 100, message = CommonConstants.INVALID_TRANSACTION_COU_LENGTH)
	private String counterparty;

	@Size(max = 255, message = CommonConstants.INVALID_TRANSACTION_TD_LENGTH)
	private String transactionDescription;
}
