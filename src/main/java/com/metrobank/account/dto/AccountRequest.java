package com.metrobank.account.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountRequest {

	@NotBlank(message = "Customer name is required field")
	@Size(max = 50)
	private String customerName;

	@NotBlank(message = "Customer mobile is required field")
	@Size(max = 20)
	private String customerMobile;

	@NotBlank(message = "Email is required field")
	@Email(message = "Email is invalid")
	@Size(max = 50)
	private String customerEmail;

	@NotBlank(message = "Address1 is required field")
	@Size(max = 100)
	private String address1;

	@Size(max = 100)
	private String address2;

	// sample payload skipped this but table says required S or C
	@NotBlank(message = "Account type is required field")
	@Pattern(regexp = "S|C", message = "Account type must be S or C")
	private String accountType;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
}
