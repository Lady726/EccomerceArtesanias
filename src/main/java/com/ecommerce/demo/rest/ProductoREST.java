package com.ecommerce.demo.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.model.Producto;
import com.ecommerce.demo.service.ProductoService;



@RestController
@RequestMapping ("/api/producto/")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductoREST {
	
	@Autowired
	private ProductoService productoService;
	
	@PostMapping
	private ResponseEntity<Producto> guardar (@RequestBody Producto producto){
		Producto temporal = productoService.create(producto);
		
		try {
			return ResponseEntity.created(new URI("/api/producto"+temporal.getId())).body(temporal);
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	
	@GetMapping
	private ResponseEntity<List<Producto>> listarTodosLosProductos (){
		return ResponseEntity.ok(productoService.getAllProducto());
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> eliminarProducto(@PathVariable("id") Long id){
		productoService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping (value = "{id}")
	private ResponseEntity<Optional<Producto>> listarPersonasPorID (@PathVariable ("id") Long id){
		return ResponseEntity.ok(productoService.findById(id));
	}
	@PutMapping("{id}")
	private ResponseEntity<Producto> actualizarProducto(@PathVariable("id") Long id, @RequestBody Producto producto) {
		Producto updatedProducto = productoService.update(id, producto);
		if (updatedProducto != null) {
			return ResponseEntity.ok(updatedProducto);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	

}