package com.grupo08.card.service;

import com.grupo08.card.models.Card;
import com.grupo08.card.repository.CardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CardService implements ICardService {
	@Autowired
	private CardRepository repository;

	@Override
	public Flux<Card> findAll() {
		return repository.findAll();
	}

	@Override
	public Mono<Card> findById(String id) {
		return repository.findById(id);
	}
	
	/*
	@Override
	public boolean findByNumdocument(String d) {
		Boolean result = false;
		Mono<Card> reg = repository.findByNumdocument(d).next();
		result = reg.block() != null;
		return result;
	}
	*/
	
	@Override
	public Flux<Card> findByNumdocument(String d) {
		return repository.findByNumdocument(d);
	}
	
	@Override
	public Mono<Card> save(Card c) {
		return repository.save(c);
	}

	@Override
	public Mono<Void> delete(Card c) {
		return repository.delete(c);
	}
	
}
