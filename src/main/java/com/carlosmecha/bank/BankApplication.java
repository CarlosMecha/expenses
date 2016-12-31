package com.carlosmecha.bank;

import com.carlosmecha.bank.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Main entrypoint for the application.
 */
@SpringBootApplication
@EntityScan(basePackageClasses = User.class)
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}
}
