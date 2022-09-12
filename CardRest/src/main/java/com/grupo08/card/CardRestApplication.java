package com.grupo08.card;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.grupo08.card.models.Card;
import com.grupo08.card.repository.CardRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Slf4j
public class CardRestApplication implements CommandLineRunner{

	@Autowired
	private CardRepository repository;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	public static void main(String[] args){
		SpringApplication.run(CardRestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("tarjetas").subscribe();
		Flux.just(
				new Card("20220100001","21525252","c","1","1","","", LocalDate.of(2019, Month.JULY, 20), LocalDate.now()),
				new Card("20220100002","21525123","d","3","","3","", LocalDate.of(2019, Month.JULY, 10), LocalDate.now()),
				new Card("20220100003","21525123","c","","","","", LocalDate.of(2019, Month.JULY, 10), LocalDate.now()))
				.flatMap(p -> {
					return repository.save(p);
				}).subscribe(p -> log.info("Insert: " + p.toString()));
		
	}

}
