package com.grupo04.customer;

import com.grupo04.customer.models.CustomerBusiness;
import com.grupo04.customer.models.CustomerPersonal;
import com.grupo04.customer.repository.CustomerBusinessRepository;
import com.grupo04.customer.repository.CustomerPersonalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.Month;

@EnableEurekaClient
@SpringBootApplication
@Slf4j
public class CustomerApplication implements CommandLineRunner {
	@Autowired
	private CustomerPersonalRepository repositorycp;
	@Autowired
	private CustomerBusinessRepository repositorycb;
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("customerpersonal").subscribe();
		Flux.just(
				new CustomerPersonal("20220818001", "Jose", "Liza", "21525252", "jose@micorreo.com", "98765432", true,
						LocalDate.of(2019, Month.JULY, 20), LocalDate.now()),
				new CustomerPersonal("20220818002", "Julian", "Cespedes", "21525123", "julian@micorreo.com", "98765412",
						true, LocalDate.of(2019, Month.JULY, 20), LocalDate.now()),
				new CustomerPersonal("20220818003", "Maria", "Flores", "21525321", "maria@micorreo.com", "98765423",
						false, LocalDate.of(2019, Month.JULY, 20), LocalDate.now()),
				new CustomerPersonal("20220818004", "Melissa", "Solier", "21525345", "melissa@micorreo.com", "98765445",
						false, LocalDate.of(2019, Month.JULY, 20), LocalDate.now()),
				new CustomerPersonal("20220818005", "Lupe", "Pariona", "21525678", "lupe@micorreo.com", "98765455",
						true, LocalDate.of(2019, Month.JULY, 20), LocalDate.now()),
				new CustomerPersonal("20220818006", "Oscar", "Benavides", "215223454", "oscar@micorreo.com", "98765499",
						false, LocalDate.of(2019, Month.JULY, 20), LocalDate.now()))
				.flatMap(p -> {
					return repositorycp.save(p);
				}).subscribe(p -> log.info("Insert: " + p.toString()));
		mongoTemplate.dropCollection("customerbusiness").subscribe();
		Flux.just(
				new CustomerBusiness("20220998001", "Claro", "10215252520", "Lima - Perú", "98765432",
						LocalDate.of(2019, Month.JULY, 20), LocalDate.now()),
				new CustomerBusiness("20220998002", "Movistar", "12215251234", "Breña - Lima", "98765412",
						LocalDate.of(2019, Month.JULY, 20), LocalDate.now()),
				new CustomerBusiness("20220998003", "Epson", "14215253212", "Miami - EEUU", "98765423",
						LocalDate.of(2019, Month.JULY, 20), LocalDate.now()),
				new CustomerBusiness("20220998004", "Global2000", "10215253457", "Casuarinas - Lima", "98765445",
						LocalDate.of(2019, Month.JULY, 20), LocalDate.now()),
				new CustomerBusiness("20220998005", "DELL", "12215256788", "Silicon Valley", "98765455",
						LocalDate.of(2019, Month.JULY, 20), LocalDate.now()),
				new CustomerBusiness("20220998006", "ASUS", "162152234547", "La Molina - Lima", "98765499",
						LocalDate.of(2019, Month.JULY, 20), LocalDate.now()))
				.flatMap(p -> {
					return repositorycb.save(p);
				}).subscribe(p -> log.info("Insert: " + p.toString()));
	}
}