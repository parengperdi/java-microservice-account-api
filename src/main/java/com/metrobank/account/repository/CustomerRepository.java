package com.metrobank.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metrobank.account.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
