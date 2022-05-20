package edu.bu.metcs673.trackr.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.domain.User;
import edu.bu.metcs673.trackr.service.UserService;

/**
 * Controller for Users Management. Contains a single 'Create' API for new users
 * to register with the application.
 * 
 * @author Tim Flucker
 *
 */
@Controller
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<GenericApiResponse> createUser(@Valid @RequestBody User userInput) {

		return new ResponseEntity<GenericApiResponse>(userService.createUser(userInput), HttpStatus.ACCEPTED);
	}
}
