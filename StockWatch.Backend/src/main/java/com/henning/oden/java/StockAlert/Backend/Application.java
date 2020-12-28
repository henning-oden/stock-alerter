package com.henning.oden.java.StockAlert.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.henning.oden.java.StockAlert.Backend")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
