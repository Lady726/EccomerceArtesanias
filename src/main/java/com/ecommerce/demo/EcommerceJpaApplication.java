package com.ecommerce.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

// Anotación que marca la clase principal como una aplicación Spring Boot
@SpringBootApplication
public class EcommerceJpaApplication {

	public static void main(String[] args) {SpringApplication.run(EcommerceJpaApplication.class, args);}

	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI()
				.info(new Info()
						.title("prueba de swagger spring boot 3 API")
						.version("1.0.0")
						.description("Ejemplo de swagger construccion de software")
						.termsOfService("htttps://swagger.io/terms")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}