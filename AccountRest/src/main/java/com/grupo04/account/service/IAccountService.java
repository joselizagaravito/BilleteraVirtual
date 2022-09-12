package com.grupo04.account.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.grupo04.account.models.Account;
import com.grupo04.account.models.ResponseProducts;

public interface IAccountService {

    public List<Account> findAll();
    public Optional<Account> findById(Long id) ;
    public List<Account> findByCustomerId(String customerId);
	public Optional<Account> findFirstByNumaccount(String numaccount);
    public Optional<Account> save(Account account);
    public void delete(Account account);
    public ResponseEntity<ResponseProducts> findProductsByDocument(String document);
}