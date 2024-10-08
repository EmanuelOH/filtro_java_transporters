package com.irwi.transporters.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl{
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;
    public void sendTaskNotificationEmail(String toEmail, String projectName, String projectDescription){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(toEmail);
        message.setSubject("Nuevo proyecto asignado: " + projectName);
        message.setText("Has sido asignado a la siguiente tarea: " + projectDescription);
        mailSender.send(message);
    }
}
