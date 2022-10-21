package com.tutor.notificationservice.controller;

import com.tutor.notificationservice.entity.Response;
import com.tutor.notificationservice.entity.TutorEmail;
import com.tutor.notificationservice.service.NotificationPublisher;
import com.tutor.notificationservice.service.TutorEmailNotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/notify")
@Slf4j
public class NotificationController {
    @Autowired
    private TutorEmailNotificationService notificationService;

    @Autowired
    private NotificationPublisher notificationPublisher;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    static final String TOPIC = "First Message in Kafka";

      @PostMapping("/email")
    public Response sendEmail(@RequestBody TutorEmail tutorEmail){
        log.info("The emil sent is {}", tutorEmail);
        return notificationService.sendEmail(tutorEmail);
    }
    @PostMapping(value = "/kafkaAPI")
    public String sendMessageToKafkaTopic( @RequestBody TutorEmail email) {
        this.notificationPublisher.sendMessage(email);
        log.info("Email Accepted Successful");
        return "Successfully joined the group";
    }
    @PostMapping("/publish")
    public ResponseEntity<String> sendMessage( @RequestBody TutorEmail email) {
        this.notificationPublisher.sendMessage(email);
        notificationPublisher.sendMessage(email);
        return ResponseEntity.ok("Email has sent to the user");
    }
}
