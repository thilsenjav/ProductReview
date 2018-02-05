package com.demo.product.review.integration;

import static org.junit.Assert.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RestStepDefs extends SpringIntegrationTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestStepDefs.class);
	
	@When("^client requests POST (.*) with JSON data$")
	public void performPostOnURIWithJsonData(String uri,String jsonData) throws Exception {
		
		
		
		/*resultActions = this.mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON)
				.content(jsonData.getBytes()).headers(headers));*/
		
		//String url = createUrl(uri);
		LOGGER.info("actual url "+uri);
		LOGGER.info("JSON data "+jsonData);
		/*HttpEntity<String> httpEnt = new HttpEntity<>(jsonData,httpHeaders);
		responseEnt = restTemplate.exchange(url, HttpMethod.POST, httpEnt, String.class);*/
		executePost(uri, jsonData);
		
	}
	
	@Then("^response code should be (\\d*)$")
	public void checkResponseCode(int respCode) throws Exception {
		
		//resultActions.andExpect(status().is(respCode));
		int actualRespCode = latestResponse.getTheResponse().getStatusCode().value();
		LOGGER.info("checkResponseCode "+respCode +" actualRespCode: "+actualRespCode);
		assertEquals("Response Code is not matcing"+latestResponse.getBody(),respCode,actualRespCode);
	}
	
	@Then("^Response body should be '(.*)'$")
	public void checkResponseBodyStringMatch(String expRespMessage) throws Exception {
		
		//resultActions.andExpect(content().string(respMessage));
		
		LOGGER.info("checkResponseBodyStringMatch : expRespMessage "+expRespMessage);
		assertEquals("Response body is not matcing"+latestResponse.getBody(),latestResponse.getBody(),expRespMessage);
	}
	
}
