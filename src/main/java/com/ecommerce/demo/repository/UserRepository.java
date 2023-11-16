package com.ecommerce.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.demo.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

  // Método para buscar un usuario por su nombre
  User findByName(String name);
  // Método para buscar un usuario por su correo electrónico
  User findByEmail(String email);
  // Método para verificar si existe un usuario con un correo electrónico específico
  boolean existsByEmail(String email);
}