package com.demo.product.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaRepositories
public class ProductReviewApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ProductReviewApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
	return new RestTemplate();
	}
}
