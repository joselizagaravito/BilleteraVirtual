package com.grupo04.customer.repository;

import com.grupo04.customer.models.CustomerPersonal;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CustomerPersonalRepository extends ReactiveMongoRepository<CustomerPersonal,String> {
    Flux<CustomerPersonal> findByDni(String dni);
}
