package com.demo.product.review.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.demo.product.review.domain.User;
import com.demo.product.review.service.ServiceException;
import com.demo.product.review.service.UserService;


@RunWith(SpringRunner.class)
@WebMvcTest(value=UserController.class,secure=false)
public class UserControllerTests {
	private static final Logger LOGGER =LoggerFactory.getLogger(UserControllerTests.class);

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	private User user=new User(1L,"senthil", "senthil@gmail.com", "pwd12345");
			
	@Test
	public void givenId_WhenGetUser_ThenReturnJson() throws Exception {
		LOGGER.info("in givenId_WhenGeUser_ThenReturnJson ----");
		String  userJSON= "{" 
				   + "                \"name\": \"senthil\"," 
				   + "                \"email\": \"senthil@gmail.com\","                  
				   + "                \"password\" : \"pwd12345\"}"; 

		Mockito.when(userService.get(Mockito.anyLong())).thenReturn(user);
		RequestBuilder requestBuilder =MockMvcRequestBuilders.get("/api/user/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response=result.getResponse();
		assertEquals("Expected code did not match", 200,response.getStatus());
		JSONAssert.assertEquals(userJSON, response.getContentAsString(), false);
	}
	
	@Test
	public void givenInvalidId_WhenGetUser_ThenReturnJson() throws Exception {
		LOGGER.info("in givenInvalidId_WhenGetUser_ThenReturnJson ----");
		String expectedErrorJson="{"
				+ "\"code\":\"INVALID_ID\","
				+ "\"message\":\"Invalid Id\"}";
		
		Mockito.when(userService.get(Mockito.anyLong())).thenThrow(new ServiceException("INVALID_ID","Invalid Id"));
		RequestBuilder requestBuilder =MockMvcRequestBuilders.get("/api/user/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response=result.getResponse();
		assertEquals("Expected code did not match", 400,response.getStatus());
		JSONAssert.assertEquals(expectedErrorJson, response.getContentAsString(), false);
	}
	

	
}
