package com.demo.product.review.controller;

import java.net.URI;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.product.review.domain.User;
import com.demo.product.review.service.UserService;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;;


@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;

	private static final Logger LOGGER =LoggerFactory.getLogger(UserController.class);
	@PostMapping("/")
	public ResponseEntity<?> create(@RequestBody @Valid User user){
		long savedUserId=userService.create(user);
		LOGGER.info("savedUserId : "+savedUserId);
		HttpHeaders httpHeaders=new HttpHeaders();
		URI location =linkTo(methodOn(this.getClass()).getById(savedUserId)).toUri();
		LOGGER.info("location : "+location.toString());
		httpHeaders.setLocation(location);
		return new ResponseEntity<String>("User registration successful",HttpStatus.OK);
	} 

	@GetMapping(value="/{id}")
	public ResponseEntity<User> getById(@PathVariable long id){
		
		return null;
	}
}
