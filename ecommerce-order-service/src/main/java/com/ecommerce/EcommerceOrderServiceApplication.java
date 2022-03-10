package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EcommerceOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceOrderServiceApplication.class, args);
	}

}
