package com.demo.product.review.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import com.demo.product.review.domain.User;
import com.demo.product.review.repository.UserRepository;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

//@RunWith(SpringRunner.class)



public class UserStepDefs extends SpringIntegrationTest{
	
	@Autowired
	UserRepository userRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserStepDefs.class);
	///*private MockMvc mvc;*/
	
	
	
	@Given("^User DB is empty")
	public void emptyDB()throws Throwable{
		userRepository.deleteAll();
	}
	
	
	@Then("^Response header should have '(.*)' with value '(.*)'$")
	public void checkHeaderMatch(String headerName,String expHeaderValue) {
		
		/*String actualHeaderValue =resultActions.andReturn().getResponse().getHeaderValue(headerName).toString();
		
		assertEquals(expHeaderValue, actualHeaderValue);*/
		LOGGER.info("checkHeaderMatch : headerName "+headerName +" : expHeaderValue "+expHeaderValue);
		HttpHeaders httpHeaders = latestResponse.getTheResponse().getHeaders();
		assertEquals("Header is not matcing"+httpHeaders.getFirst(headerName).toString(),httpHeaders.getFirst(headerName).toString(),expHeaderValue);
		
		
		
	}
	
	@Then("^user should be stored in DB with id (\\d*)$")
	public void checkUserIdinDB(long id) throws Exception {
		
		User user = userRepository.findOne(id);
		assertNotNull(user);
	}

	@Given("^following user exist$")
	public void createUsers(DataTable users) {
		List<User> usersList = users.asList(User.class);
		userRepository.save(usersList);
	}
	
	@Given("^Web context is setup")
	public void webContest( ) throws Exception{
	}
	
	
	
}
