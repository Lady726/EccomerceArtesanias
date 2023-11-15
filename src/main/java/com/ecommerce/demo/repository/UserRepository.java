package com.ecommerce.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.demo.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

  User findByName(String name);
  User findByEmail(String email);
  boolean existsByEmail(String email);
}