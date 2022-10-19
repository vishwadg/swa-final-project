package com.tutor.notificationservice.controller;

import com.tutor.notificationservice.entity.Response;
import com.tutor.notificationservice.entity.TutorEmail;
import com.tutor.notificationservice.service.TutorEmailNotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    KafkaTemplate<String, String> kafkaTemplate;
    static final String TOPIC = "First Message in Kafka";

    @GetMapping("/kafka/{message}")
    public String getAllEmails(@PathVariable("message")String message){
        kafkaTemplate.send(TOPIC, message);
        return "Message Published on Kafka platform, Horay!";

    }
    @PostMapping("/email")
    public Response sendEmail(@RequestBody TutorEmail tutorEmail){
        log.info("The emil sent is {}", tutorEmail);
        return notificationService.sendEmail(tutorEmail);
    }


}
