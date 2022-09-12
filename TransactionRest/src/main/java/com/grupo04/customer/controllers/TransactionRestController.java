package com.grupo04.customer.controllers;

import com.grupo04.customer.models.Transaction;
import com.grupo04.customer.service.ITransactionService;
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
@RequestMapping
public class TransactionRestController {

    @Autowired
    private ITransactionService service;

    @GetMapping
    public Flux<Transaction> listar(Model model) {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Transaction>> detail(@PathVariable String id) {
        return service.findById(id).map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> save(@Valid @RequestBody Mono<Transaction> monoTransaction) {
        Map<String, Object> result = new HashMap<String, Object>();
        return monoTransaction.flatMap(transaction -> {
            return service.save(transaction).map(p -> {
                result.put("Movimiento", p);
                result.put("mensaje", "Transacción guardada con éxito");
                return ResponseEntity
                        .created(URI.create("/api/transaction".concat(transaction.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(result);
            });
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

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Transaction>> edit(@RequestBody Transaction transaction, @PathVariable String id) {
        return service.findById(id).flatMap(p -> {
                    return service.save(p);
                }).map(p -> ResponseEntity.created(URI.create("/api/transaction".concat(p.getId())))
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
    
    @GetMapping("/listlastten/{documentnumber}")
    public Flux<Transaction> listLastTenTransactionCard(@PathVariable String documentnumber){
    	return service.listlastten(documentnumber);
    }
}

