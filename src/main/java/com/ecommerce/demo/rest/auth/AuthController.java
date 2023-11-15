package com.ecommerce.demo.rest.auth;

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


  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody LoginUserDto loginUserDto) {

    log.info("Login with JWT");

    var response = loginUserService.doLogin(loginUserDto);

    JWTResponseApiDto responseJWT = new JWTResponseApiDto();

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

    log.info("Register with JWT");

    var response = serviceRegister.createUser(userRegisterDto);

    return new ResponseEntity<>(response, response.getStatus());

  }
}
