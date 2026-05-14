package com.example.zbd;

import com.example.zbd.repositories.CustomerRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ZbdApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZbdApplication.class, args);
	}

	@Bean
	public CommandLineRunner testLogging(CustomerRepository customerRepository) {
		return args -> {
			System.out.println(">>> TESTING DATABASE LOGGING <<<");

			customerRepository.findAll();

			System.out.println(">>> TEST COMPLETED - CHECK log.txt <<<");
		};
	}
}
