package com.metrobank.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {

	private Long customerNumber;
	private Integer transactionStatusCode;
	private String transactionStatusDescription;

	public Long getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}

	public Integer getTransactionStatusCode() {
		return transactionStatusCode;
	}

	public void setTransactionStatusCode(Integer transactionStatusCode) {
		this.transactionStatusCode = transactionStatusCode;
	}

	public String getTransactionStatusDescription() {
		return transactionStatusDescription;
	}

	public void setTransactionStatusDescription(String transactionStatusDescription) {
		this.transactionStatusDescription = transactionStatusDescription;
	}
}
