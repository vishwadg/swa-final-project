package com.tutor.notificationservice.controller;

import com.tutor.notificationservice.dto.ResponseDto;
import com.tutor.notificationservice.emailconfiguration.ApplicationConstant;
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
    @PostMapping(value = "/kafkaAPI")
    public ResponseEntity<ResponseDto> sendMessageToKafkaTopic(@RequestBody TutorEmail email) {
        this.notificationPublisher.sendMessage(email);
        log.info("Email Accepted Successful");
        return ResponseEntity.accepted().body(
                        ResponseDto.builder()
                                .message(ApplicationConstant.EMAIL_SUCCESSFULLY_ACCEPTED)
                                .message(ApplicationConstant.ACCEPTED)
                                .build());
    }
}
