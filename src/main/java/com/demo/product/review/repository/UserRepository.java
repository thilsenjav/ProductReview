package com.demo.product.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.product.review.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
