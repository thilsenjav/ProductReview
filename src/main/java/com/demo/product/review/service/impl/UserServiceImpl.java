package com.demo.product.review.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.product.review.controller.UserController;
import com.demo.product.review.domain.User;
import com.demo.product.review.repository.UserRepository;
import com.demo.product.review.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	private static final Logger LOGGER =LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public long create(User user) {
		User savedUser=userRepository.save(user);
		LOGGER.info("service --- "+savedUser.getId());
		return savedUser.getId();
	}

}
