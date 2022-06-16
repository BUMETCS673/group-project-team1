package edu.bu.metcs673.trackr.bankaccount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.MessageFormat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.user.TrackrUser;
import edu.bu.metcs673.trackr.user.TrackrUserService;

@ExtendWith(MockitoExtension.class)
public class BankAccountControllerTest {

	public static final TrackrUser TEST_USER = new TrackrUser(0L, "Test", "Tester", "testTest", "blahblah",
			"test@email.com");
	public static final BankAccount.ACCOUNT_TYPE TEST_TYPE = BankAccount.ACCOUNT_TYPE.CHECKING;
	public static final String ACCOUNT_DESC = "TEST DESCRIPTION";
	public static final double TEST_BALANCE = 1234.56;

	@Mock
	private BankAccountService bankAccountService;

	@Mock
	private TrackrUserService userService;

	@InjectMocks
	private BankAccountController controller;

	@Test
	public void testAnnotationValidations() {

		BankAccountDTO bankDTO = new BankAccountDTO(TEST_TYPE, ACCOUNT_DESC, TEST_BALANCE);
		BankAccount mockBankAccount = new BankAccount(1L, TEST_USER, TEST_TYPE, ACCOUNT_DESC, TEST_BALANCE,
				BankAccount.ACCOUNT_STATUS.ACTIVE,null);

		ResponseEntity<GenericApiResponse<BankAccount>> mockResponse = ResponseEntity.ok(GenericApiResponse
				.successResponse(MessageFormat.format(CommonConstants.CREATE_BANK_ACCOUNT, mockBankAccount.getId()),
						mockBankAccount));

		Mockito.when(userService.getCurrentUser()).thenReturn(TEST_USER);
		Mockito.when(bankAccountService.createBankAccount(bankDTO, TEST_USER)).thenReturn(mockBankAccount);

		ResponseEntity<GenericApiResponse<BankAccount>> response = controller.createBankAccount(bankDTO);

		assertEquals(mockResponse, response);
	}
}
