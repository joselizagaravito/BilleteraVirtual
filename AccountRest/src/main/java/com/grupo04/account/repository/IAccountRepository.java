package com.grupo04.account.repository;

import com.grupo04.account.models.Account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepository extends JpaRepository<Account, Long> {
	List<Account> findByCustomerId(String customerId);
	Optional<Account> findFirstByNumaccount(String numaccount);
}
