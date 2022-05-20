package edu.bu.metcs673.trackr.service.impl;

import java.util.List;

import edu.bu.metcs673.trackr.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.bu.metcs673.trackr.domain.User;
import edu.bu.metcs673.trackr.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Defines logic of the "UserService" methods. Calls methods in the
 * 'UserRepository' class to get / save data.
 * 
 * @author Tim Flucker
 *
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> retrieveAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserById(long id) {
		log.info("Searching for record id: " + id);
		User user = userRepository.findById(id);
		return user;
	}

	@Override
	public void createUser(String username, String password, String email) {
		// TODO Auto-generated method stub

	}

}
