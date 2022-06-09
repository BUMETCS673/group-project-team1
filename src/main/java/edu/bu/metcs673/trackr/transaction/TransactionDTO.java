package edu.bu.metcs673.trackr.transaction;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import edu.bu.metcs673.trackr.common.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Xiaobing Hou
 * @date 05/21/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class TransactionDTO {

    @NotNull(message = CommonConstants.TRANSACTION_MONEY)
    private Long bankAccountId;

    @NotNull(message = CommonConstants.TRANSACTION_MONEY)
    private Double money;

    @NotNull(message = CommonConstants.TRANSACTION_TIME)
    private String time;

    @NotNull(message = CommonConstants.TRANSACTION_COU)
    @Size(max = 100, message = CommonConstants.INVALID_TRANSACTION_COU_LENGTH)
    private String counterparty;

    @Size(max = 255, message = CommonConstants.INVALID_TRANSACTION_TD_LENGTH)
    private String transactionDescription;
}
