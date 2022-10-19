package com.tutor.notificationservice.service;

import com.tutor.notificationservice.emailconfiguration.TutorEmailConfiguration;
import com.tutor.notificationservice.entity.Response;
import com.tutor.notificationservice.entity.TutorEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TutorEmailNotificationImpl implements TutorEmailNotificationService {


    @Autowired
    private TutorEmailConfiguration tutorEmailConfiguration;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public Response sendEmail(TutorEmail email) {
        Response response = new Response();
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            tutorEmailConfiguration.getJavaMailSender().send(message);
            response.setCode(0);
            response.setMessage("Email sent Ok");
        } catch (Exception e) {
            response.setCode(1);
            response.setMessage("Error on sending email" + response.getMessage());
        }
        return response;
    }
    @Override
    public Optional<TutorEmail> findAllById(Long id) {
        return Optional.empty();
    }
}