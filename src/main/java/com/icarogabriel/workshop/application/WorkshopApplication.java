package com.icarogabriel.workshop.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.icarogabriel.workshop")
@EntityScan(basePackages = "com.icarogabriel.workshop.entities")
@EnableJpaRepositories(basePackages = "com.icarogabriel.workshop.repositories")
public class WorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkshopApplication.class, args);
	}

}
