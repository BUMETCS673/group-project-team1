package edu.bu.metcs673.trackr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main class which is run to initialize the application, set context,
 * initialize the embedded H2 database, and expose the API endpoints.
 * 
 * @author Tim Flucker
 *
 */
@SpringBootApplication
@EnableJpaRepositories("com.trackr.repo")
@ComponentScan("com.trackr")
public class TrackrApp {

	public static void main(String[] args) {
		SpringApplication.run(TrackrApp.class, args);
	}

}
