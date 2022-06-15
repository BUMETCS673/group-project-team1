package edu.bu.metcs673.trackr.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import edu.bu.metcs673.trackr.common.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class TokenRetrievalDTO {

	@NotBlank(message = CommonConstants.BLANK_USERNAME)
	@Size(min = 1, max = 50, message = CommonConstants.INVALID_USERNAME_LENGTH)
	private String username;

	// extra column size is to account for hashed value
	@NotBlank(message = CommonConstants.BLANK_PASSWORD)
	@Size(max = 200, message = CommonConstants.INVALID_PASSWORD_LENGTH)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
}
