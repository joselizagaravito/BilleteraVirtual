package com.grupo04.customer.controllers;

import com.grupo04.customer.models.CustomerBusiness;
import com.grupo04.customer.models.CustomerPersonal;
import com.grupo04.customer.service.ICustomerBusinessService;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/customerbusiness")
@Slf4j
public class BusinessRestController {
	@Autowired
	private ICustomerBusinessService service;

	@GetMapping
	public Flux<CustomerBusiness> listar(Model model) {
		log.info("lista de registros");
		return service.findAll();
	}

	@GetMapping("/ruc/{ruc}")
	public Mono<CustomerBusiness> findByRuc(@PathVariable String ruc) {
		log.info("Ruc: " + ruc);
		return service.findByRuc(ruc).next();
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<CustomerBusiness>> detail(@PathVariable String id) {
		return service.findById(id).map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(
						ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(new CustomerBusiness()));
	}

	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> save(@Valid @RequestBody Mono<CustomerBusiness> monoCustomer) {
		Map<String, Object> result = new HashMap<String, Object>();
		log.info(monoCustomer.toString());
		return monoCustomer.flatMap(customer -> {
			return service.save(customer).map(p -> {
				result.put("Cliente", p);
				result.put("mensaje", "Empresa guardada con éxito");
				return ResponseEntity.created(URI.create("/api/customerbusiness/".concat(customer.getId())))
						.contentType(MediaType.APPLICATION_JSON).body(result);
			});
		}).onErrorResume(err -> {
			return Mono.just(err).cast(WebExchangeBindException.class).flatMap(e -> Mono.just(e.getFieldErrors()))
					.flatMapMany(errs -> Flux.fromIterable(errs))
					.map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
					.collectList().flatMap(list -> {
						result.put("Errors", list);
						result.put("status", HttpStatus.BAD_REQUEST.value());
						return Mono.just(ResponseEntity.badRequest().body(result));
					});
		});
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<CustomerBusiness>> edit(@RequestBody CustomerBusiness customer,
			@PathVariable String id) {
		return service.findById(id).flatMap(p -> {
			return service.save(p);
		}).map(p -> ResponseEntity.created(URI.create("/api/customerpersonal".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON).body(p)).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> edit(@PathVariable String id) {
		return service.findById(id).flatMap(p -> {
			return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
}
