package com.grupo04.customer;

import com.grupo04.customer.models.Transaction;
import com.grupo04.customer.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.time.Month;

@Slf4j
@EnableEurekaClient
@SpringBootApplication
public class TransactionApplication implements CommandLineRunner {
    @Autowired
    private TransactionRepository repository;
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mongoTemplate.dropCollection("transaction").subscribe();
        Flux.just(
                        new Transaction("20220810110001","Deposito","20220002001","20220002001" , 500.0f , LocalDateTime.of(2020, Month.JULY, 20,  10, 23), "12345678001"),
                        new Transaction("20220810110002","Retiro","20220002001","" , 100.0f , LocalDateTime.of(2020, Month.JULY, 21, 9, 20), "12345678001"),
                        new Transaction("20220810110003","PagoCredito","","20229002002" , 500f , LocalDateTime.of(2020, Month.JULY, 21, 11, 15), "12345678001")
                        )
                .flatMap(p -> {
                    return repository.save(p);
                })
                .subscribe(p -> log.info("Insert: " + p.toString()));
    }
}