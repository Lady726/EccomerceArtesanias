package com.ecommerce.demo.rest.auth;

import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    public void enviarCorreo(String destinatario, String asunto, String mensaje) throws MessagingException {
        // Configuración de las propiedades para la conexión SMTP con Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Credenciales de la cuenta de Gmail
        String usuario = "ljbarrios52@ucatolica.edu.co";
        String contraseña = "hghc odfc ffzv bwjh";

        // Se crea una sesión con las credenciales
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, contraseña);
            }
        });

        // Se crea el mensaje de correo
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(usuario));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(asunto);
        message.setText(mensaje);

        // Envío del correo
        Transport.send(message);
    }
}
