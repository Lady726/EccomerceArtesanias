package com.ecommerce.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.demo.model.UserState;



public interface UserStateRepository extends JpaRepository<UserState, Long> {


}