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
@EntityScan("edu.bu.metcs673.trackr.domain")
public class TrackrApp {

	public static void main(String[] args) {
		SpringApplication.run(TrackrApp.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
