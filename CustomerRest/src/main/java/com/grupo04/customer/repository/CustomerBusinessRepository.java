package com.grupo04.customer.repository;

import com.grupo04.customer.models.CustomerBusiness;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CustomerBusinessRepository extends ReactiveMongoRepository<CustomerBusiness,String> {
    Flux<CustomerBusiness> findByRuc(String ruc);
}
