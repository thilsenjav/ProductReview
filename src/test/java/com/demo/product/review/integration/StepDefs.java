package com.demo.product.review.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.demo.product.review.ProductReviewApplication;
import com.demo.product.review.domain.User;
import com.demo.product.review.repository.UserRepository;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT,classes=ProductReviewApplication.class)
@TestPropertySource(locations="classpath:application.properties")
@AutoConfigureMockMvc

public class StepDefs {

	@Autowired
	UserRepository userRepository;
	
	private MockMvc mockMvc;
	private ResultActions resultActions;
	private HttpHeaders httpHeaders=new HttpHeaders(); 
	
	@Test
	@Given("^DB is empty")
	public void emptyDB() throws Throwable 
	{
		userRepository.deleteAll();
	}
	
	@When("^client requests POST (.*) with JSON data$")
	public void performPostOnURIwithJsonData(String uri,String jsonData) throws Exception {
		resultActions=this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON)
				.content(jsonData.getBytes()).headers(httpHeaders));
	}
	
	@Then("^response code should be (\\d*)$")
	public void checkResponseCode(int resCode) throws Exception {
		resultActions.andExpect(status().is(resCode));
	}
	
	@Then("^Response body should have a success message '(.*)'$")
	public void checkResponseBodyStringMatch(String resMsg) throws Exception{
		resultActions.andExpect(content().string(resMsg));
	}
	
	@Then("^Response header should have '(.*)' with value '(.*)'$")
	public void checkHeaderMatch(String headerName,String headerValue) throws Exception{
		String actualHeaderValue=resultActions.andReturn().getResponse().getHeaderValue(headerName).toString();
		assertEquals(headerValue, actualHeaderValue);
	}
	
	@Then("^user should be stored in DB with id (\\d)$")
	public void checkUserIdInDB(long id) throws Exception{
		User user1=userRepository.findOne(id);
		assertNotNull(user1);
	}
	
	@Given("^following users exist$")
	public void createUsers(DataTable users) {
		List<User> usersList=users.asList(User.class);
		userRepository.save(usersList);
	}
	
	
	
}