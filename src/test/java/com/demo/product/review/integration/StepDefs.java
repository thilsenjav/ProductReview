package com.demo.product.review.integration;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.product.review.ProductReviewApplication;
import com.demo.product.review.repository.UserRepository;

import cucumber.api.java.en.Given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT,classes=ProductReviewApplication.class)
@TestPropertySource(locations="classpath:application.properties")
@AutoConfigureMockMvc

public class StepDefs {

	@Autowired
	UserRepository userRepository;
	
	@Given("^DB is empty")
	public void emptyDB() throws Throwable 
	{
		userRepository.deleteAll();
	}
	
	
}
