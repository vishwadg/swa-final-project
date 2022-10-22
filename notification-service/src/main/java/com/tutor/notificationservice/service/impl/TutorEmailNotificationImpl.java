package com.tutor.notificationservice.service.impl;

import com.tutor.notificationservice.configs.EmailConfig;
import com.tutor.notificationservice.entity.Response;
import com.tutor.notificationservice.entity.Email;
import com.tutor.notificationservice.service.TutorEmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class TutorEmailNotificationImpl implements TutorEmailNotificationService {

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public Response sendEmail(Email email) {
        Response response = new Response();
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            emailConfig.getJavaMailSender().send(message);
            response.setCode(0);
            response.setMessage("Email sent Ok");
        } catch (Exception e) {
            response.setCode(1);
            response.setMessage("Error on sending email" + response.getMessage());
        }
        return response;
    }
}