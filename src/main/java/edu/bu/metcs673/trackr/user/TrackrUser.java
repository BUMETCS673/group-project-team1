package edu.bu.metcs673.trackr.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import edu.bu.metcs673.trackr.common.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Define a Trackr object, defining it as an Entity related to a table called
 * "USERS" in the H2 database. Other annotations (Data, NoArgsConstructor,
 * AllArgsConstructor) are related to Lombok and generally save developers from
 * writing a lot of repetitive code.
 * 
 * @author Tim Flucker
 *
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@Validated
public class TrackrUser {

	// defines the id column, used as a unique identifier
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 100)
	@NotBlank(message = CommonConstants.BLANK_FIRST_NAME)
	@Size(min = 1, max = 100, message = CommonConstants.INVALID_FIRST_NAME_LENGTH)
	private String firstName;

	@Column(length = 100)
	@NotBlank(message = CommonConstants.BLANK_LAST_NAME)
	@Size(min = 1, max = 100, message = CommonConstants.INVALID_LAST_NAME_LENGTH)
	private String lastName;

	// code below defines different columns in the table (USERNAME, PASSWORD, EMAIL)
	@Column(nullable = false, length = 50)
	@NotBlank(message = CommonConstants.BLANK_USERNAME)
	@Size(min = 1, max = 50, message = CommonConstants.INVALID_USERNAME_LENGTH)
	private String username;

	// extra column size is to account for hashed value
	@Column(nullable = false, length = 200)
	@NotBlank(message = CommonConstants.BLANK_PASSWORD)
	@Size(min = 6, max = 200, message = CommonConstants.INVALID_PASSWORD_LENGTH)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Column(nullable = false, length = 100)
	@NotBlank(message = CommonConstants.BLANK_EMAIL)
	@Size(max = 50, message = CommonConstants.INVALID_EMAIL_LENGTH)
	@Email(regexp = CommonConstants.EMAIL_REGEX, message = CommonConstants.INVALID_EMAIL_FORMAT)
	private String email;
	
}
