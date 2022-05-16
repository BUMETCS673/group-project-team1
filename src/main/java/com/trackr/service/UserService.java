package com.trackr.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.trackr.domain.User;

/**
 * Interface which defines methods which will be implemented in the
 * "UserServiceImpl". This interface is reusable, so other classes could extend
 * this if they wanted / needed to.
 * 
 * @author Tim Flucker
 *
 */
@Component
public interface UserService {

	public List<User> retrieveAllUsers();

	public User findUserById(long id);

	public void createUser(String username, String password, String email);

}
