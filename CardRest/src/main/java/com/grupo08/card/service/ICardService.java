package com.grupo08.card.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.grupo08.card.models.Card;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICardService {
	public Flux<Card> findAll();
    public Mono<Card> findById(String id);
    public Flux<Card> findByNumdocument(String doc);
    public Mono<Card> save(@RequestBody Card monoCard);
    public Mono<Void> delete(Card c);
}
