package com.ecommerce.demo.rest.auth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Clase que representa un DTO (Data Transfer Object) para las respuestas relacionadas con el token JWT
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class JWTResponseApiDto {
  // Mensaje relacionado con la respuesta (puede ser un mensaje descriptivo)
  String message;
  // Token JWT devuelto como parte de la respuesta
  String token;
}
