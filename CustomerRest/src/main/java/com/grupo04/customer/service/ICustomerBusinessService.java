package com.grupo04.customer.service;

import com.grupo04.customer.models.CustomerBusiness;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerBusinessService {
    public Flux<CustomerBusiness> findAll();
    public Mono<CustomerBusiness> findById(String id);
    public Flux<CustomerBusiness> findByRuc(String ruc);
    public Mono<CustomerBusiness> save(CustomerBusiness customer);
    public Mono<Void> delete(CustomerBusiness customer);
}
