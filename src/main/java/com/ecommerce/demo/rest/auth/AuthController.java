package com.ecommerce.demo.rest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.rest.dto.PasswordResetRequestDto;
import com.ecommerce.demo.service.EmailService;
import com.ecommerce.demo.service.auth.LoginUserDto;
import com.ecommerce.demo.service.auth.LoginUserService;
import com.ecommerce.demo.service.auth.RegisterUserService;
import com.ecommerce.demo.service.auth.UserRegisterDto;


import org.springframework.web.bind.annotation.RequestBody;

import lombok.extern.slf4j.Slf4j;

// Controlador REST para operaciones de autenticación y registro de usuarios
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

  @Autowired
  RegisterUserService serviceRegister;

  @Autowired
  LoginUserService loginUserService;
  @Autowired
  private EmailService emailService;
  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody LoginUserDto loginUserDto) {

    log.info("Login with JWT");

    var response = loginUserService.doLogin(loginUserDto);

    // Creación de un objeto DTO de respuesta para el token JWT y el mensaje
    JWTResponseApiDto responseJWT = new JWTResponseApiDto();

    // Comprobación del estado de la respuesta y configuración de los datos de
    // respuesta
    if (response.getStatus() == HttpStatus.CREATED) {
      responseJWT.setMessage("token");
      responseJWT.setToken(response.getData().get(0).toString());
    } else {
      responseJWT.setMessage("User No valid");
      responseJWT.setToken("---");
    }

    return new ResponseEntity<>(responseJWT, response.getStatus());

  }
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto) {

    // Registro de información utilizando el logger
    log.info("Register with JWT");

    var response = serviceRegister.createUser(userRegisterDto);

    // Devolución de una respuesta con los datos de respuesta y el estado HTTP
    // correspondiente
    return new ResponseEntity<>(response, response.getStatus());

  }
  @PostMapping("/request-password-reset")
  public ResponseEntity<?> requestPasswordReset(@RequestBody PasswordResetRequestDto requestDto) {
    // Aquí debes generar un token y guardarlo con una relación al usuario
    String token = "token_generado"; // Reemplaza con tu lógica de generación de token

    // Enviar correo electrónico
    emailService.sendPasswordResetEmail(requestDto.getEmail(), token);

    return ResponseEntity.ok("Correo de cambio de contraseña enviado.");
  }
  
}