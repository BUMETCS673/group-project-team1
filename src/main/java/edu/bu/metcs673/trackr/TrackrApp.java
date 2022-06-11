package edu.bu.metcs673.trackr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Main class which is run to initialize the application, set context,
 * initialize the embedded H2 database, and expose the API endpoints.
 * 
 * @author Tim Flucker
 *
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "edu.bu.metcs673.trackr")
@EntityScan("edu.bu.metcs673.trackr")
public class TrackrApp {

	/**
	 * Main method of the Trackr application. Initializes context and sets up
	 * application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(TrackrApp.class, args);
	}

	/**
	 * A third-party library configured using a bean which is used for encrypting
	 * password value
	 * 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
