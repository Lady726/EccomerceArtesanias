package com.ecommerce.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@ecommerce.com");
        message.setTo(to);
        message.setSubject("Cambio de Contraseña");
        message.setText("Para cambiar tu contraseña, usa este token: " + token);
        mailSender.send(message);
    }
}