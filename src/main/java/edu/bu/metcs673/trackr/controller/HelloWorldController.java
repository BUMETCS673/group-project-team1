package edu.bu.metcs673.trackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.bu.metcs673.trackr.constants.CommonConstants;
import edu.bu.metcs673.trackr.domain.User;
import edu.bu.metcs673.trackr.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Test Controller used for Iteration 0 as a proof of concept and as a teaching
 * tool for team members who do not have extensive coding experience.
 * 
 * @author Tim Flucker
 *
 */
@Slf4j
@RestController
@RequestMapping(path = "/hello")
public class HelloWorldController {

	// @Slf4j - logging library useful for logging and debugging, requires Lombok to
	// be installed
	
	// @RestController - defines this class as a controller, which contains API
	// endpoints, sets up application context behind the scenes
	
	// @RequestMapping - define the 'high level' mapping for this controller

	// declared here to allow us to utilize methods in the 'UserService' project
	// @Autowired allows for us to inject beans and other dependencies related to UserService
	@Autowired
	private UserService userService;

	// @GetMapping defines this API as a HTTP GET method
	@GetMapping
	public String helloWorld() {
		// utilizing logger here for informational purposes, should be viewable in console log
		log.info("Returning hello world message...");
		
		// call to CommonConstants file to return a static string
		return CommonConstants.HELLO_WORLD_MSG;
	}

	// define method as get method, with a specific path - localhost:8080/hello/tim
	@GetMapping(path = "/tim")
	public User getTim() {
		log.info("Returning sample 'tim' record...");
		
		// call the 'findUserById' method in the 'UserService' 
		User tim = userService.findUserById(1);
		
		// call logger for informational purposes.
		log.info(tim.toString());
		
		// return user object
		return tim;
	}

}
