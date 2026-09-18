package com.metrobank.account.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metrobank.account.dto.AccountRequest;
import com.metrobank.account.dto.AccountResponse;
import com.metrobank.account.dto.CustomerResponse;
import com.metrobank.account.dto.SavingsAccountResponse;
import com.metrobank.account.entity.Account;
import com.metrobank.account.entity.Customer;
import com.metrobank.account.exception.CustomerNotFoundException;
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

	@Transactional(readOnly = true)
	public CustomerResponse find(Long customerNumber) {
		Customer customer = customerRepository.findById(customerNumber)
				.orElseThrow(CustomerNotFoundException::new);

		List<SavingsAccountResponse> savings = new ArrayList<>();
		for (Account account : customer.getAccounts()) {
			SavingsAccountResponse item = new SavingsAccountResponse();
			item.setAccountNumber(account.getAccountNumber());
			item.setAccountType(toAccountTypeName(account.getAccountType()));
			item.setAvailableBalance(account.getAvailableBalance());
			savings.add(item);
		}

		CustomerResponse response = new CustomerResponse();
		response.setCustomerNumber(customer.getCustomerNumber());
		response.setCustomerName(customer.getCustomerName());
		response.setCustomerMobile(customer.getCustomerMobile());
		response.setCustomerEmail(customer.getCustomerEmail());
		response.setAddress1(customer.getAddress1());
		response.setAddress2(customer.getAddress2());
		response.setSavings(savings);
		response.setTransactionStatusCode(302);
		response.setTransactionStatusDescription("Customer Account found");
		return response;
	}

	private String toAccountTypeName(String accountType) {
		if ("C".equals(accountType)) {
			return "Checking";
		}
		return "Savings";
	}
}
