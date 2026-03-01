package com.challenge_2026.punto_de_venta_acc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.challenge_2026.punto_de_venta_acc.repository")
@EnableCaching
public class PuntoDeVentaAccApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuntoDeVentaAccApplication.class, args);
	}

}
