package com.ecommerce.demo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.demo.model.Producto;
import com.ecommerce.demo.repository.ProductoRepository;



@Service
public class ProductoService {
	
	@Autowired
	private ProductoRepository productoResporitory;
	
	
	public Producto create (Producto producto) {
		return productoResporitory.save(producto);
	}
	
	public List<Producto> getAllProducto (){
		return productoResporitory.findAll();
	}
	
	public void delete(Long id) {
		productoResporitory.deleteById(id);
	}
	
	
	public Optional<Producto> findById (Long id) {
		return productoResporitory.findById(id);
	}
	 public Producto update(Long id, Producto producto) {
	        if(productoResporitory.findById(id).isPresent()) {
	            producto.setId(id);
	            return productoResporitory.save(producto);
	        }
	        else {
	            // Aquí podrías lanzar una excepción o manejarlo de otra forma
	            return null;
	        }
	    }
	

}