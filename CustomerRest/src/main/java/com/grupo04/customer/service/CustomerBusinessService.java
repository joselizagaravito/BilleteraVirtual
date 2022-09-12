package com.grupo04.customer.service;

import com.grupo04.customer.models.CustomerBusiness;
import com.grupo04.customer.repository.CustomerBusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerBusinessService implements ICustomerBusinessService {
	@Autowired
	private CustomerBusinessRepository repository;

	@Override
	public Flux<CustomerBusiness> findAll() {
		return repository.findAll();
	}

	@Override
	public Mono<CustomerBusiness> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Flux<CustomerBusiness> findByRuc(String ruc) {
		return repository.findByRuc(ruc);
	}

	@Override
	public Mono<CustomerBusiness> save(CustomerBusiness customer) {
		return repository.save(customer);
	}

	@Override
	public Mono<Void> delete(CustomerBusiness customer) {
		return repository.delete(customer);
	}
}
