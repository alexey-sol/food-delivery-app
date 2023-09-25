package com.github.alexeysol.fooddelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ApiFoodDeliveryApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiFoodDeliveryApplication.class, args);
	}
}
