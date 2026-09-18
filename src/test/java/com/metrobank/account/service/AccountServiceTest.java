package com.metrobank.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.metrobank.account.dto.AccountRequest;
import com.metrobank.account.dto.AccountResponse;
import com.metrobank.account.dto.CustomerResponse;
import com.metrobank.account.entity.Account;
import com.metrobank.account.entity.Customer;
import com.metrobank.account.exception.CustomerNotFoundException;
import com.metrobank.account.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private AccountService accountService;

	@Test
	void create_shouldReturn201() {
		AccountRequest request = new AccountRequest();
		request.setCustomerName("Test");
		request.setCustomerMobile("09081234567");
		request.setCustomerEmail("test12345@gmail.com");
		request.setAddress1("test");
		request.setAddress2("test");
		request.setAccountType("S");

		when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
			Customer customer = invocation.getArgument(0);
			customer.setCustomerNumber(1L);
			return customer;
		});

		AccountResponse response = accountService.create(request);

		assertEquals(201, response.getTransactionStatusCode());
		assertEquals("Customer account created", response.getTransactionStatusDescription());
		assertEquals(1L, response.getCustomerNumber());
	}

	@Test
	void find_shouldReturnCustomer() {
		Customer customer = new Customer();
		customer.setCustomerNumber(1L);
		customer.setCustomerName("Test");
		customer.setCustomerMobile("09081234567");
		customer.setCustomerEmail("test12345@gmail.com");
		customer.setAddress1("test");

		Account account = new Account();
		account.setAccountNumber(1L);
		account.setAccountType("S");
		account.setCustomer(customer);
		customer.getAccounts().add(account);

		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

		CustomerResponse response = accountService.find(1L);

		assertEquals(302, response.getTransactionStatusCode());
		assertEquals("Customer Account found", response.getTransactionStatusDescription());
		assertEquals("Savings", response.getSavings().get(0).getAccountType());
		assertEquals(500.0, response.getSavings().get(0).getAvailableBalance());
	}

	@Test
	void find_shouldThrowWhenMissing() {
		when(customerRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(CustomerNotFoundException.class, () -> accountService.find(99L));
	}
}
