package com.grupo04.customer.repository;

import com.grupo04.customer.models.Transaction;

import reactor.core.publisher.Flux;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
	
	Flux<Transaction> findBySourceAccount(String sourceAccount);
	Flux<Transaction> findTop10BySourceAccountOrderByDateDesc(String sourceAccount);
	
}
