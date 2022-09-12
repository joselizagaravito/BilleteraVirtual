package com.grupo08.card.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.grupo08.card.models.Card;

import reactor.core.publisher.Flux;

public interface CardRepository extends ReactiveMongoRepository<Card,String> {
    Flux<Card> findByNumdocument(String numdocument);
}
