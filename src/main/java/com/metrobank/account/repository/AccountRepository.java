package com.metrobank.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metrobank.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
