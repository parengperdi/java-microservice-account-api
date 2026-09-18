package com.metrobank.account.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrobank.account.dto.AccountRequest;
import com.metrobank.account.dto.AccountResponse;
import com.metrobank.account.service.AccountService;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request) {
		AccountResponse response = accountService.create(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
