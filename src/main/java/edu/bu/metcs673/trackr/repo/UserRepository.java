package edu.bu.metcs673.trackr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.bu.metcs673.trackr.domain.User;

/**
 * Directly interfaces with the "USERS" table in the H2 repository, using the JPA library.
 * @author Tim Flucker
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	public User findById(long id);
}
