package com.metrobank.account.service;

import org.springframework.stereotype.Service;

import com.metrobank.account.dto.AccountRequest;
import com.metrobank.account.dto.AccountResponse;
import com.metrobank.account.entity.Account;
import com.metrobank.account.entity.Customer;
import com.metrobank.account.repository.CustomerRepository;

@Service
public class AccountService {

	private final CustomerRepository customerRepository;

	public AccountService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public AccountResponse create(AccountRequest request) {
		Customer customer = new Customer();
		customer.setCustomerName(request.getCustomerName());
		customer.setCustomerMobile(request.getCustomerMobile());
		customer.setCustomerEmail(request.getCustomerEmail());
		customer.setAddress1(request.getAddress1());
		customer.setAddress2(request.getAddress2());

		Account account = new Account();
		account.setAccountType(request.getAccountType());
		account.setCustomer(customer);
		customer.getAccounts().add(account);

		Customer saved = customerRepository.save(customer);

		AccountResponse response = new AccountResponse();
		response.setCustomerNumber(saved.getCustomerNumber());
		response.setTransactionStatusCode(201);
		response.setTransactionStatusDescription("Customer account created");
		return response;
	}
}
