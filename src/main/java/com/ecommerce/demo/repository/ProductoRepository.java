package com.ecommerce.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.demo.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

}