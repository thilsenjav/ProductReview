package com.demo.product.review.security.rest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.demo.product.review.dto.UserDto;
import com.demo.product.review.service.UserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

	private UserDetailsService userDetailsService;

	private User principal;

	public LoginFilter(String urlMapping,UserDetailsService userDetailsService,AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(urlMapping));
		this.userDetailsService = userDetailsService;
		this.setAuthenticationManager(authenticationManager);
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
		final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
		principal, user.getPassword(), principal.getAuthorities());
		Authentication authentication = getAuthenticationManager().authenticate(loginToken);
		return authentication;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		/*final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
				authResult, null, authResult.getAuthorities());
		*/
		SecurityContextHolder.getContext().setAuthentication(authResult); 
		//response.addHeader("X-AUTH-TOKEN", authResult);
	
		
	}
	
	
}
