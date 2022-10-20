package com.tutor.notificationservice.service;

import com.tutor.notificationservice.emailconfiguration.ApplicationConstant;
import com.tutor.notificationservice.entity.TutorEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationPublisher {

@Autowired
private KafkaTemplate<String, TutorEmail> kafkaTemplate;
    public void sendMessage(TutorEmail email) {
        log.info("Email send to Process.");
        this.kafkaTemplate.send(ApplicationConstant.TOPIC_NAME, email);
    }

}
