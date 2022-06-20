package edu.bu.metcs673.trackr.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import edu.bu.metcs673.trackr.common.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class TrackrUserDTO {

    @NotBlank(message = CommonConstants.BLANK_FIRST_NAME)
    @Size(min = 1, max = 100, message = CommonConstants.INVALID_FIRST_NAME_LENGTH)
    private String firstName;

    @NotBlank(message = CommonConstants.BLANK_LAST_NAME)
    @Size(min = 1, max = 100, message = CommonConstants.INVALID_LAST_NAME_LENGTH)
    private String lastName;

    @Size(max = 50, message = CommonConstants.INVALID_USERNAME_LENGTH)
    private String username;

    // extra column size is to account for hashed value
    @Size(max = 200, message = CommonConstants.INVALID_PASSWORD_LENGTH)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @Size(max = 200, message = CommonConstants.INVALID_PASSWORD_LENGTH)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String newPassword;

    @NotBlank(message = CommonConstants.BLANK_EMAIL)
    @Size(max = 50, message = CommonConstants.INVALID_EMAIL_LENGTH)
    @Email(regexp = CommonConstants.EMAIL_REGEX, message = CommonConstants.INVALID_EMAIL_FORMAT)
    private String email;

    public TrackrUserDTO(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
