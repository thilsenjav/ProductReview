package com.demo.product.review.security.rest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import com.demo.product.review.controller.UserController;
import com.demo.product.review.dto.UserDto;
import com.demo.product.review.service.UserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger LOGGER =LoggerFactory.getLogger(UserController.class);
	
	private UserDetailsService userDetailsService;

	private User principal;
	private TokenAuthenticationService tokenAuthenticationService;
	
	private UsernamePasswordAuthenticationToken loginToken;
	
	public LoginFilter(String urlMapping,UserDetailsService userDetailsService,AuthenticationManager authenticationManager,TokenAuthenticationService tokenAuthenticationService) {
		super(new AntPathRequestMatcher(urlMapping));
		this.userDetailsService = userDetailsService;
		this.setAuthenticationManager(authenticationManager);
		this.tokenAuthenticationService=tokenAuthenticationService;
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		final UserDto user = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);
		if (user == null || StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
		throw new AuthenticationServiceException("Cannot authenticate. Invalid data posted.");
		}
		
		// Lookup the complete User object from the database and create an Authentication for it
		principal = (User) userDetailsService.loadUserByUsername(user.getUserName());
		loginToken = new UsernamePasswordAuthenticationToken(
		principal, user.getPassword(), principal.getAuthorities());
		Authentication authentication = getAuthenticationManager().authenticate(loginToken);
		LOGGER.debug("Loginfilter : attemptAuthentication : "+loginToken);
		LOGGER.debug("Loginfilter : attemptAuthentication principal : "+principal);
		return authentication;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		/*final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
				authResult, null, authResult.getAuthorities());
		*/
		LOGGER.debug("Loginfilter : successfulAuthentication : "+loginToken);
		SecurityContextHolder.getContext().setAuthentication(loginToken); 
		//response.addHeader("X-AUTH-TOKEN", authResult);
		
		tokenAuthenticationService.addAuthentication(response, loginToken); // for successful login, adding headers
		chain.doFilter(request, response);
		
	}
	
	
}
