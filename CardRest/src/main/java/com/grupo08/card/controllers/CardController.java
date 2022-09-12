package com.grupo08.card.controllers;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.grupo08.card.models.Card;
import com.grupo08.card.service.ICardService;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/card")
@Slf4j
public class CardController {

	// @Autowired
	// private CircuitBreakerFactory cbFactory;

	@Autowired
	private ICardService service;

	@GetMapping
	public Flux<Card> listar(Model model) {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Mono<Card> detail(@PathVariable String id) {
		return service.findById(id);
	}

	public Mono<Card> detailA(String id) {
		return service.findById(id);
	}

	public Mono<Card> detailB(String id) {
		return service.findById(id);
	}
	
	@GetMapping("/document/{doc}")
	public Flux<Card> findByNumdocument(@PathVariable String doc) {
		return service.findByNumdocument(doc);
	}
	
	@PostMapping("/save")
	public Mono<ResponseEntity<Map<String, Object>>> save(@RequestBody Card monoCard) {
		Map<String, Object> result = new HashMap<String, Object>();
		log.info(monoCard.toString());
		return	service.save(monoCard).map(p -> {
			result.put("CurrentAccount", monoCard.getCurrentaccount());
			result.put("Document", monoCard.getNumdocument());
			result.put("message", "Se genero la tarjeta");
			result.put("status", true);
			return ResponseEntity.created(URI.create("/api/savings/".concat(p.getId().toString())))
					.contentType(MediaType.APPLICATION_JSON).body(result);
		}).onErrorResume(err -> {
            return Mono.just(err).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(errs -> Flux.fromIterable(errs))
                    .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        result.put("Errors", list);
                        result.put("status", HttpStatus.BAD_REQUEST.value());
                        return Mono.just(ResponseEntity.badRequest().body(result));
                    });
        });
	}
}