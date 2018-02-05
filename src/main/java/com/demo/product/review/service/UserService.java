package com.demo.product.review.service;

import com.demo.product.review.domain.User;

public interface UserService {

	public long create(User user);
	
	public User get(Long Id);
	
}
