package edu.bu.metcs673.trackr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Define a User object, defining it as an Entity related to a table called
 * "USERS" in the H2 database. Other annotations (Data, NoArgsConstructor,
 * AllArgsConstructor) are related to Lombok and generally save developers from
 * writing a lot of repetitive code.
 * 
 * @author Tim Flucker
 *
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User {

	// defines the id column, used as a unique identifier
	@Id
	private long id;

	// code below defines different columns in the table (USERNAME, PASSWORD, EMAIL)
	@Column(nullable = false, length = 50)
	private String username;

	@Column(nullable = false, length = 100)
	private String password;

	@Column(nullable = false, length = 100)
	private String email;
}
