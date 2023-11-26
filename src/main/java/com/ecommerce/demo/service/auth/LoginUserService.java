package com.ecommerce.demo.service.auth;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.demo.config.JwtUtil;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.repository.UserRepository;



@Service
public class LoginUserService {
    // Asumiendo que ya tienes estas dependencias inyectadas y constantes definidas
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final int MAX_FAILED_ATTEMPTS = 5; // Máximo de intentos fallidos antes de bloquear
    private final int LOCK_DURATION_MINUTES = 30; // Duración del bloqueo en minutos

    public ResponseApi doLogin(LoginUserDto loginUserDto) {
        String email = loginUserDto.getEmail();
        String password = loginUserDto.getPassword();

        // Validar que los campos no estén vacíos
        if (email.isEmpty() || password.isEmpty()) {
            return new ResponseApi(
                "Should send data",
                HttpStatus.I_AM_A_TEAPOT,
                LocalDateTime.now());
        }

        // Validar si el usuario existe en la base de datos
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new ResponseApi(
                "User does not exist",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now());
        }

        // Comprobar si la cuenta está bloqueada
        if (user.isAccountLocked() && !user.isLockTimeExpired()) {
            return new ResponseApi(
                "Account is locked. Try again later.",
                HttpStatus.UNAUTHORIZED,
                LocalDateTime.now());
        }

        // Validar que la contraseña sea correcta
        String storedPassword = user.getPassword();
        if (!passwordEncoder.matches(password, storedPassword)) {
            user.incrementFailedAttempts();
            if (user.getFailedLoginAttempts() >= MAX_FAILED_ATTEMPTS) {
                user.setLockTime(LocalDateTime.now().plusMinutes(LOCK_DURATION_MINUTES));
            }
            userRepository.save(user);
            return new ResponseApi(
                "Invalid password",
                HttpStatus.UNAUTHORIZED,
                LocalDateTime.now());
        }

        // Si la validación es exitosa, reiniciar los intentos fallidos y generar el token JWT
        user.resetFailedAttempts();
        userRepository.save(user);

        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken(email);

        return new ResponseApi(
            "User logged in successfully",
            HttpStatus.CREATED,
            LocalDateTime.now(),
            token);
    }
}

