package com.grupo04.customer.service;

import com.grupo04.customer.models.CustomerPersonal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerPersonalService {
    public Flux<CustomerPersonal> findAll();
    public Mono<CustomerPersonal> findById(String id);
    public Flux<CustomerPersonal> findByDni(String dni);
    public Mono<CustomerPersonal> save(CustomerPersonal customer);
    public Mono<Void> delete(CustomerPersonal customer);
}
