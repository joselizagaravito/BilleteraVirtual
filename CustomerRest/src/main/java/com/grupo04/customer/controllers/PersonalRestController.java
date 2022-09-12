package com.grupo04.customer.controllers;

import com.grupo04.customer.models.CustomerPersonal;
import com.grupo04.customer.service.ICustomerPersonalService;
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
@RequestMapping("/customerpersonal")
public class PersonalRestController {
    private static final Logger log = LoggerFactory.getLogger(PersonalRestController.class);
    @Autowired
    private ICustomerPersonalService service;

    @GetMapping
    public Flux<CustomerPersonal> listar(Model model) {
        log.info("lista de registros");
        return service.findAll();
    }

    @GetMapping("/dni/{dni}")
    public Mono<CustomerPersonal> findByDni(@PathVariable String dni) {
        return service.findByDni(dni).next();
    }
    
    @GetMapping("/{id}")
    public Mono<ResponseEntity<CustomerPersonal>> detail(@PathVariable String id) {
        return service.findById(id).map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new CustomerPersonal()));
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> save(@Valid @RequestBody Mono<CustomerPersonal> monoCustomer) {
        Map<String, Object> result = new HashMap<String, Object>();
        return monoCustomer.flatMap(customer -> {
            return service.save(customer).map(p -> {
                result.put("Cliente", p);
                result.put("mensaje", "Cliente guardado con éxito");
                return ResponseEntity
                        .created(URI.create("/api/customerpersonal/".concat(customer.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(result);
            });
        }).onErrorResume(err -> {
        	log.info("ingreso a onErrorResume");
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

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CustomerPersonal>> edit(@RequestBody CustomerPersonal customer, @PathVariable String id) {
        return service.findById(id).flatMap(p -> {
                    return service.save(p);
                }).map(p -> ResponseEntity.created(URI.create("/api/customerpersonal".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> edit(@PathVariable String id) {
        return service.findById(id).flatMap(p -> {
            return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}

