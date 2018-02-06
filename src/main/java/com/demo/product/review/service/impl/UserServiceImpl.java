package com.demo.product.review.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.product.review.controller.UserController;
import com.demo.product.review.domain.User;
import com.demo.product.review.repository.UserRepository;
import com.demo.product.review.service.ServiceException;
import com.demo.product.review.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	private static final Logger LOGGER =LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public long create(User user) {
		user.setPassword(encryptPassword(user.getPassword()));
		User savedUser=userRepository.save(user);
		LOGGER.info("service --- "+savedUser.getId());
		return savedUser.getId();
	}

	@Override
	public User get(Long Id) {
		User user=userRepository.findOne(Id);
		LOGGER.info("user service impl findOne --- "+user);
		
		if(null==user) {
			throw new ServiceException("INVALID_ID","Invalid Id");
		}
		return user; 
	}

	private String encryptPassword(String password) {
		LOGGER.info("password Before encrypt: "+password);
		String encrptedPwd=new BCryptPasswordEncoder().encode(password);
		LOGGER.info("password After encrypt: "+encrptedPwd);
		return encrptedPwd;
	}
}
