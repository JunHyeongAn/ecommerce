package com.ecommerce.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EcommerceApigatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApigatewayServiceApplication.class, args);
	}

}
