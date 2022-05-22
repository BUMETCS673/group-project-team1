package edu.bu.metcs673.trackr.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import edu.bu.metcs673.trackr.api.GenericApiResponse;
import edu.bu.metcs673.trackr.common.TrackrInputValidationException;

/**
 * Used to handle the custom exceptions that are generated by the application.
 * Returns an error response to the user.
 * 
 * @author Tim Flucker
 *
 */
@ControllerAdvice
public class ExceptionController {

	/**
	 * If a TrackrInputValidationException is thrown, then this block of code will
	 * execute.
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = TrackrInputValidationException.class)
	protected ResponseEntity<GenericApiResponse> handleValidationExceptions(TrackrInputValidationException exception,
			WebRequest request) {
		return new ResponseEntity<GenericApiResponse>(GenericApiResponse.errorResponse(exception.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * If a ConstraintViolationException or MethodArgumentNotValidException is
	 * thrown, then this block of code will execute. All error messages that are
	 * captured in the validation will be returned to the user as a Map string.
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { ConstraintViolationException.class, MethodArgumentNotValidException.class })
	protected ResponseEntity<GenericApiResponse> handleValidationExceptions(MethodArgumentNotValidException exception,
			WebRequest request) {

		Map<String, String> errorMap = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errorMap.put(fieldName, errorMessage);
		});

		return new ResponseEntity<GenericApiResponse>(GenericApiResponse.errorResponse(errorMap.toString()),
				HttpStatus.BAD_REQUEST);
	}
}