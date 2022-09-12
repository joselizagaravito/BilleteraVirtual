package com.grupo08.yanki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WalletRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletRestApplication.class, args);
	}

}
