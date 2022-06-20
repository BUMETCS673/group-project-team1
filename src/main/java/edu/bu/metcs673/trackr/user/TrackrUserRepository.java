package edu.bu.metcs673.trackr.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Directly interfaces with the "USERS" table in the H2 repository, using the
 * JPA library.
 * 
 * @author Tim Flucker
 *
 */
@Repository
public interface TrackrUserRepository extends JpaRepository<TrackrUser, Long> {

	/**
	 * Custom JPA query that checks to see if a record with the provided 'username'
	 * already exists in the DB.
	 * 
	 * @param username
	 * @return boolean
	 */
	public boolean existsByUsername(String username);

	/**
	 * Custom JPA query that finds a user by its 'username' value.
	 * 
	 * @param username
	 * @return
	 */
	public TrackrUser findByUsername(String username);

	/**
	 * Custom JPA query that checks to see if a record with the provided 'email'
	 * already exists in the DB.
	 * 
	 * @param username
	 * @return boolean
	 */
	public boolean existsByEmail(String email);
}
