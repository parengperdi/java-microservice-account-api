package com.metrobank.account.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String CREATE_BODY = "{"
			+ "\"customerName\":\"Test\","
			+ "\"customerMobile\":\"09081234567\","
			+ "\"customerEmail\":\"test12345@gmail.com\","
			+ "\"address1\":\"test\","
			+ "\"address2\":\"test\","
			+ "\"accountType\":\"S\""
			+ "}";

	@Test
	void createAccount_success() throws Exception {
		mockMvc.perform(post("/api/v1/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(CREATE_BODY))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.transactionStatusCode").value(201))
				.andExpect(jsonPath("$.customerNumber").exists());
	}

	@Test
	void createAccount_emailRequired() throws Exception {
		String body = "{"
				+ "\"customerName\":\"Test\","
				+ "\"customerMobile\":\"09081234567\","
				+ "\"address1\":\"test\","
				+ "\"accountType\":\"S\""
				+ "}";

		mockMvc.perform(post("/api/v1/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.transactionStatusCode").value(400))
				.andExpect(jsonPath("$.transactionStatusDescription").value("Email is required field"));
	}

	@Test
	void getAccount_found() throws Exception {
		MvcResult created = mockMvc.perform(post("/api/v1/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(CREATE_BODY))
				.andExpect(status().isCreated())
				.andReturn();

		JsonNode json = objectMapper.readTree(created.getResponse().getContentAsString());
		long customerNumber = json.get("customerNumber").asLong();

		mockMvc.perform(get("/api/v1/account/" + customerNumber))
				.andExpect(status().isFound())
				.andExpect(jsonPath("$.transactionStatusCode").value(302))
				.andExpect(jsonPath("$.savings[0].availableBalance").value(500.0));
	}

	@Test
	void getAccount_notFound() throws Exception {
		mockMvc.perform(get("/api/v1/account/99999999"))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.transactionStatusCode").value(401))
				.andExpect(jsonPath("$.transactionStatusDescription").value("Customer not found"));
	}
}
