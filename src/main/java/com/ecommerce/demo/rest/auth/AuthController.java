package com.ecommerce.demo.rest.auth;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.service.auth.LoginUserDto;
import com.ecommerce.demo.service.auth.LoginUserService;
import com.ecommerce.demo.service.auth.RegisterUserService;
import com.ecommerce.demo.service.auth.UserRegisterDto;

import org.springframework.web.bind.annotation.RequestBody;


import com.ecommerce.demo.rest.auth.*; // Importa tu servicio de correo


// Controlador REST para operaciones de autenticación y registro de usuarios
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmailService emailService; // Inyecta el servicio de correo

    @Autowired
  RegisterUserService serviceRegister;

  @Autowired
  LoginUserService loginUserService;



  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto) {

  
      var response = serviceRegister.createUser(userRegisterDto);
  
      if (response.getStatus() == HttpStatus.CREATED) {
          // Envío de correo electrónico al registrar un nuevo usuario
          String destinatario = userRegisterDto.getEmail(); // Obtén el correo electrónico del usuario registrado
          String asunto = "Registro exitoso";
          String cuerpo = "¡Hola " + userRegisterDto.getName() + "! Has sido registrado exitosamente.";
  
          try {
            emailService.enviarCorreo(destinatario, asunto, cuerpo);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      }
  
      return new ResponseEntity<>(response, response.getStatus());
  }
  




  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody LoginUserDto loginUserDto) {

    var response = loginUserService.doLogin(loginUserDto);

    // Creación de un objeto DTO de respuesta para el token JWT y el mensaje
    JWTResponseApiDto responseJWT = new JWTResponseApiDto();

    // Comprobación del estado de la respuesta y configuración de los datos de respuesta
    if (response.getStatus() == HttpStatus.CREATED) {
      responseJWT.setMessage("token");
      responseJWT.setToken(response.getData().get(0).toString());
    } else {
      responseJWT.setMessage("User No valid");
      responseJWT.setToken("---");
    }

    return new ResponseEntity<>(responseJWT, response.getStatus());

  }



}
