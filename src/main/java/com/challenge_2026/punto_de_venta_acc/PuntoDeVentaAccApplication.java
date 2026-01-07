package com.challenge_2026.punto_de_venta_acc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.challenge_2026.punto_de_venta_acc.repository")
@SpringBootApplication
public class PuntoDeVentaAccApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuntoDeVentaAccApplication.class, args);
	}

}
