package com.demo.product.review.service.impl;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.product.review.domain.User;
import com.demo.product.review.repository.UserRepository;
import com.demo.product.review.service.ServiceException;
import com.demo.product.review.service.UserService;


@RunWith(SpringRunner.class)
public class UserServiceImplTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplTests.class);
	
	@MockBean
	private UserRepository userRepository;
	
	@TestConfiguration
	static class UserServceImplTestConfiguration{
		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
		
	}
	@Autowired
	private UserService userService;
	
	@Before
	public void setUp() {
	
		User user=new User(1L,"senthil", "senthil@gmail.com", "pwd12345");
		Mockito.when(userRepository.findOne(Matchers.eq(1L))).thenReturn(user);
		Mockito.when(userRepository.findOne(AdditionalMatchers.not(Matchers.eq(1L)))).thenReturn(null);
	
	}
	
	@Test
	public void whenValidId_thenUserShouldBeFound(){
		User user = userService.get(1L);
		LOGGER.info("User Obj : "+user);
		assertThat(user.getId()).isEqualTo(1L);
	}
	
	@Test(expected=ServiceException.class)
	public void whenInValidId_thenShouldThrowServiceException(){
		User user = userService.get(2L);
	}
	
	
	
	
}
