package com.tutor.notificationservice.service;

import com.tutor.notificationservice.emailconfiguration.ApplicationConstant;
import com.tutor.notificationservice.entity.TutorEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationPublisher {

@Autowired
private KafkaTemplate<String, TutorEmail> kafkaTemplate;
    public void sendMessage(TutorEmail email) {
        log.info(String.format("Message sent -> %s", email.toString()));
        Message<TutorEmail> message = MessageBuilder
                .withPayload(email)
                .setHeader(KafkaHeaders.TOPIC, ApplicationConstant.TOPIC_NAME)
                .build();
        kafkaTemplate.send(message);
    }

}
