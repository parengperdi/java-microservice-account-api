package com.metrobank.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.metrobank.account.dto.AccountResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<AccountResponse> handleValidation(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
		AccountResponse response = new AccountResponse();
		response.setTransactionStatusCode(400);
		response.setTransactionStatusDescription(message);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<AccountResponse> handleNotFound(CustomerNotFoundException ex) {
		AccountResponse response = new AccountResponse();
		response.setTransactionStatusCode(401);
		response.setTransactionStatusDescription("Customer not found");
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
}
