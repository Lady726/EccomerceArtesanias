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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping ("/api/producto/")
@CrossOrigin(origins = "http://localhost:3000")

public class ProductoREST {
	
	@Autowired
	private ProductoService productoService;
	 @Operation(summary = "post product ")
	@ApiResponses(value = { 
  		@ApiResponse(responseCode = "200", description = "Found the book", 
		content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class)) }),
  		@ApiResponse(responseCode = "400", description = "Invalid id supplied", 
		content = @Content), 
  		@ApiResponse(responseCode = "404", description = "Book not found",
		 content = @Content) } )
 
	@PostMapping
	private ResponseEntity<Producto> guardar (@RequestBody Producto producto){
		Producto temporal = productoService.create(producto);
		
		try {
			// Devuelve una respuesta con estado 201 (CREATED) y la URI del recurso creado
			return ResponseEntity.created(new URI("/api/producto"+temporal.getId())).body(temporal);
			
		}catch (Exception e) {
			// Si hay una excepción, devuelve una respuesta con estado 400 (BAD_REQUEST)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@Operation(summary = "Get product")
	@ApiResponses(value = { 
  		@ApiResponse(responseCode = "200", description = "Found the book", 
		content = { @Content(mediaType = "application/json",
		 schema = @Schema(implementation = Producto.class)) }),
  		@ApiResponse(responseCode = "400", description = "Invalid id supplied",
		 content = @Content), 
  		@ApiResponse(responseCode = "404", description = "Book not found", 
		content = @Content) } )
 
	@GetMapping
	private ResponseEntity<List<Producto>> listarTodosLosProductos (){
		return ResponseEntity.ok(productoService.getAllProducto());
	}
	
	@Operation(summary = "delete product")
	@ApiResponses(value = { 
  		@ApiResponse(responseCode = "200", description = "Found the book", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class)) }),
  		@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content), 
  		@ApiResponse(responseCode = "404", description = "Book not found", content = @Content) } )
 
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> eliminarProducto(@PathVariable("id") Long id){
		productoService.delete(id);
		return ResponseEntity.ok().build();
	}
	@Operation(summary = "Get product")
	@ApiResponses(value = { 
  		@ApiResponse(responseCode = "200", description = "Found the book", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class)) }),
  		@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content), 
  		@ApiResponse(responseCode = "404", description = "Book not found", content = @Content) } )
 	
	@GetMapping (value = "{id}")
	private ResponseEntity<Optional<Producto>> listarPersonasPorID (@PathVariable ("id") Long id){
		return ResponseEntity.ok(productoService.findById(id));
	}

	@Operation(summary = "put product")
	@ApiResponses(value = { 
  		@ApiResponse(responseCode = "200", description = "Found the book", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class)) }),
  		@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content), 
  		@ApiResponse(responseCode = "404", description = "Book not found", content = @Content) } )
 
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