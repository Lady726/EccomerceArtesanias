package com.ecommerce.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	
	// Atributos que describen las características del producto
	private Long id;
	private Long codigoEan;
	private String nombreDelProducto;
	private String descripcionDelProducto;
	private String marcaArtesania;
	private Long unidadesDisponibles;
	private String imagenProducto;
	private String tipo;
	private String correo;
	private String detail;
}
