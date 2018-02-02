package com.demo.product.review.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.demo.product.review.domain.User;
import com.demo.product.review.repository.UserRepository;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserStepDefs extends IntegrationTest{

	private static final Logger LOGGER =LoggerFactory.getLogger(UserStepDefs.class);
	
	@Autowired
	UserRepository userRepository;
	
		
	@Given("^DB is empty")
	public void emptyDB() throws Throwable 
	{
		userRepository.deleteAll();
	}
	
	@Given("^Web context is setup$")
	public void web_context_is_setup() throws Throwable {

	}
	
	@When("^client requests POST (.*) with JSON data$")
	public void performPostOnURIwithJsonData(String uri,String jsonData) throws Exception {
		/*resultActions=this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON)
				.content(jsonData.getBytes()).headers(httpHeaders));*/
		String url=createUrl(uri);
		LOGGER.info("actual url : "+url);
		LOGGER.info("Submitted JSON : "+jsonData);
		LOGGER.info("restTemplate : "+restTemplate);
		HttpEntity<String> httpEnt=new HttpEntity<>(jsonData,httpHeaders);
		responseEnt=restTemplate.exchange(url,HttpMethod.POST,httpEnt, String.class);
	}
	
	@Then("^response code should be (\\d*)$")
	public void checkResponseCode(int expectedResposeCode) throws Exception {
		/*resultActions.andExpect(status().is(resCode));*/
		LOGGER.info("checkResponseCode : "+expectedResposeCode);
		int actualResponseCode=responseEnt.getStatusCodeValue();
		assertEquals("Response code is not matching "+responseEnt.getBody(),expectedResposeCode, actualResponseCode);
		
	}
	
	@Then("^Response body should be '(.*)'$")
	public void checkResponseBodyStringMatch(String expectedResMsg) throws Exception{
		/*resultActions.andExpect(content().string(resMsg));*/
		LOGGER.info("checkResponseBodyStringMatch:expectedResMsg : "+expectedResMsg);
		assertEquals("Response body is not matching "+responseEnt.getBody(),expectedResMsg, responseEnt.getBody());
	}
	
	@Then("^Response header should have '(.*)' with value '(.*)'$")
	public void checkHeaderMatch(String headerName,String headerValue) throws Exception{
		/*String actualHeaderValue=resultActions.andReturn().getResponse().getHeaderValue(headerName).toString();
		assertEquals(headerValue, actualHeaderValue);*/
		LOGGER.info("checkHeaderMatch  headerName: "+headerName +" headerValue : "+headerValue);
		String actualHeaderValue=responseEnt.getHeaders().get(headerName).toString();
		assertEquals("Header is not matching "+responseEnt.getBody(),headerValue, actualHeaderValue);
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