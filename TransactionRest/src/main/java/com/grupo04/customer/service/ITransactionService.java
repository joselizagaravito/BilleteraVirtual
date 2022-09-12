package com.grupo04.customer.service;

import com.grupo04.customer.models.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionService {
    public Flux<Transaction> findAll();
    public Mono<Transaction> findById(String id);
    public Mono<Transaction> save(Transaction customer);
    public Mono<Void> delete(Transaction customer);
    public Flux<Transaction> listlastten(String documentnumber);
}
